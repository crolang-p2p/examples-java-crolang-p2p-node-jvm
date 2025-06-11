package examples.ex_9;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.ConnectionToNodeFailedReasonException;
import org.crolangP2P.OnNewMsgHandlersBuilder;

public class Ex_9_Bob {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.BOB_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

        var onNewMsgHandlers = OnNewMsgHandlersBuilder.createNew()
            .add("CONNECT_TO_CAROL", (node, msg) -> {
                System.out.println("[CONNECT_TO_CAROL][" + node.getId() + "]");
                System.out.println("Connecting to Node " + Constants.CAROL_ID);
                connectToCarol();
            })
            .build();

        CrolangP2P.Java.allowIncomingConnections(
            new IncomingCrolangNodesCallbacks.Builder()
                .onConnectionSuccess(node ->
                    System.out.println("Connected successfully to Node " + node.getId())
                )
                .onNewMsg(onNewMsgHandlers)
                .build()
        );
    }

    private static void connectToCarol() {
        try {
            var onNewMsgHandlers = OnNewMsgHandlersBuilder.createNew()
                .add("REDIRECT_TO_ALICE", (node, msg) -> {
                    System.out.println("[REDIRECT_TO_ALICE][" + node.getId() + "]: " + msg);
                    CrolangP2P.Java.getConnectedNode(Constants.ALICE_ID).ifPresent(aliceNode -> {
                        String newMsg = msg + ", this message was redirected by Node " + Constants.BOB_ID;
                        System.out.println("Redirecting to Node " + Constants.ALICE_ID + ": " + newMsg);
                        aliceNode.send("REDIRECT_TO_ALICE", newMsg);
                    });
                })
                .build();

            CrolangP2P.Java.connectToSingleNodeSync(
                Constants.CAROL_ID,
                new SyncCrolangNodeCallbacks.Builder()
                    .onNewMsg(onNewMsgHandlers)
                    .build()
            );
            System.out.println("Connected successfully to Node " + Constants.CAROL_ID);
        } catch (ConnectionToNodeFailedReasonException e) {
            System.err.println("Failed to connect to Carol: " + e.getMessage());
        }
    }
}
