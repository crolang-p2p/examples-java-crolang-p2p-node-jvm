package examples.ex_9;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.IncomingCrolangNodesCallbacksJava;
import org.crolangP2P.java.OnNewP2PStringMsgHandlersBuilderJava;
import org.crolangP2P.java.OutgoingCrolangNodeCallbacksJava;

public class Ex_9_Bob {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> {
                    var onNewMsgHandlers = OnNewP2PStringMsgHandlersBuilderJava.createNew()
                            .add("CONNECT_TO_CAROL", (node, msg) -> {
                                System.out.println("[CONNECT_TO_CAROL][" + node.getId() + "]");
                                System.out.println("Connecting to Node " + Constants.CAROL_ID);
                                connectToCarol();
                            })
                            .build();

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            IncomingCrolangNodesCallbacksJava.builder()
                                    .onConnectionSuccess(node ->
                                            System.out.println("Connected successfully to Node " + node.getId())
                                    )
                                    .onNewStringMsg(onNewMsgHandlers)
                                    .build(),
                            () -> System.out.println("Incoming connections are now allowed"),
                            err -> System.err.println("Failed to allow incoming connections: " + err)
                    );
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);
    }

    private static void connectToCarol() {
        var onNewMsgHandlers = OnNewP2PStringMsgHandlersBuilderJava.createNew()
                .add("REDIRECT_TO_ALICE", (node, msg) -> {
                    System.out.println("[REDIRECT_TO_ALICE][" + node.getId() + "]: " + msg);
                    CrolangP2PJvm.Java.getConnectedNode(
                            Constants.ALICE_ID,
                            aliceNode -> aliceNode.ifPresent(a -> {
                                String newMsg = msg + ", this message was redirected by Node " + Constants.BOB_ID;
                                System.out.println("Redirecting to Node " + Constants.ALICE_ID + ": " + newMsg);
                                a.sendString("REDIRECT_TO_ALICE", newMsg);
                            })
                    );
                })
                .build();

        CrolangP2PJvm.Java.connectToSingleNode(
                Constants.CAROL_ID,
                OutgoingCrolangNodeCallbacksJava.builder()
                        .onNewStringMsg(onNewMsgHandlers)
                        .build()
        );
        System.out.println("Connected successfully to Node " + Constants.CAROL_ID);
    }
}
