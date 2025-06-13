package examples.ex_4;

import examples.Constants;
import org.crolangP2P.CrolangNode;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.ConnectionToNodeFailedReasonException;

public class Ex_4_A_Alice {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        SyncCrolangNodeCallbacks callbacks = SyncCrolangNodeCallbacks.builder()
            .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                .add("CHANNEL_LETTERS", (id, msg) -> System.out.println("Received a message on CHANNEL_LETTERS from Node " + id + ": " + msg))
                .add("CHANNEL_NUMBERS", (id, msg) -> System.out.println("Received a message on CHANNEL_NUMBERS from Node " + id + ": " + msg))
                .build()
            )
            .build();
        try {
            CrolangNode node = CrolangP2P.Java.connectToSingleNodeSync(Constants.BOB_ID, callbacks);

            System.out.println("Connected to Node " + Constants.BOB_ID + " successfully");

            node.send("GREETING", "Hello there!");
        } catch (ConnectionToNodeFailedReasonException e) {
            System.out.println("Failed to connect to Node " + Constants.BOB_ID + ": " + e.getReason());
        }
    }
}
