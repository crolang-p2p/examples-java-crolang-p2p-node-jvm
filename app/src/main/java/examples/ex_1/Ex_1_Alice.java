package examples.ex_1;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.ConnectionToNodeFailedReasonException;

public class Ex_1_Alice {
    public static void main(String[] args) throws ConnectToBrokerException, ConnectionToNodeFailedReasonException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        var onNewMsgHandlers = OnNewP2PMsgHandlersBuilder.createNew()
                .add("GREETINGS_CHANNEL", (node, msg) -> {
                    System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg);
                    node.send("GREETING_CHANNEL", "Hi " + node.getId() + ", I'm Node " + Constants.ALICE_ID);
                })
                .build();

        CrolangP2P.Java.connectToSingleNodeSync(
                Constants.BOB_ID,
                SyncCrolangNodeCallbacks.builder()
                        .onNewMsg(onNewMsgHandlers)
                        .build()
        );
        System.out.println("Connected to Node " + Constants.BOB_ID);
        CrolangP2P.Java.getConnectedNode(Constants.BOB_ID).ifPresent(node ->
                node.send("GREETINGS_CHANNEL", "Hello from Node " + Constants.ALICE_ID)
        );
    }
}
