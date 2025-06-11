package examples.ex_9;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.ConnectionToNodeFailedReasonException;
import org.crolangP2P.OnNewMsgHandlersBuilder;

public class Ex_9_Alice {
    public static void main(String[] args) throws ConnectionToNodeFailedReasonException, ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        var onNewMsgHandlers = OnNewMsgHandlersBuilder.createNew()
            .add("REDIRECT_TO_ALICE", (node, msg) -> {
                System.out.println("[REDIRECT_TO_ALICE][" + node.getId() + "]: " + msg);
            })
            .build();

        CrolangP2P.Java.connectToSingleNodeSync(
            Constants.BOB_ID,
            new SyncCrolangNodeCallbacks.Builder()
                .onNewMsg(onNewMsgHandlers)
                .build()
        );
        System.out.println("Connected successfully to Node " + Constants.BOB_ID);
        CrolangP2P.Java.getConnectedNode(Constants.BOB_ID).ifPresent(node -> node.send("CONNECT_TO_CAROL", ""));
    }
}
