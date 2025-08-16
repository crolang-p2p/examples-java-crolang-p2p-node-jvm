package examples.ex_7;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.OutgoingCrolangNodeCallbacks;
import org.crolangP2P.java.OnNewP2PStringMsgHandlersBuilderJava;
import org.crolangP2P.java.OutgoingCrolangNodeCallbacksJava;

public class Ex_7_Alice {
    public static void main(String[] args) {
        final int COUNTER_THRESHOLD = 20;

        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    OutgoingCrolangNodeCallbacks callbacks = OutgoingCrolangNodeCallbacksJava.builder()
                            .onDisconnection(id -> System.out.println("Disconnected from Node " + id))
                            .onNewStringMsg(OnNewP2PStringMsgHandlersBuilderJava.createNew()
                                    .add("COUNT_CHANNEL", (node, msg) -> {
                                        System.out.println("[COUNT_CHANNEL][" + node.getId() + "]: " + msg);
                                        int i = Integer.parseInt(msg);
                                        if (i >= COUNTER_THRESHOLD) {
                                            System.out.println("Counter threshold exceeded, disconnecting from Node " + node.getId());
                                            node.disconnect();
                                        } else {
                                            node.sendString("COUNT_CHANNEL", Integer.toString(i + 1));
                                        }
                                    })
                                    .build()
                            )
                            .onConnectionSuccess(node -> {
                                System.out.println("Connected successfully to Node " + node.getId());
                                System.out.println("Disconnecting from Broker...");
                                CrolangP2PJvm.Java.disconnectFromBroker(
                                        () -> CrolangP2PJvm.Java.isLocalNodeConnectedToBroker(isLocalNodeConnectedToBroker ->
                                                System.out.println("Is local Node connected to the Broker: " + isLocalNodeConnectedToBroker)
                                        ),
                                        err -> System.err.println("Failed to disconnect from Broker: " + err)
                                );
                            })
                            .build();

                    CrolangP2PJvm.Java.connectToSingleNode(Constants.BOB_ID, callbacks);
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
