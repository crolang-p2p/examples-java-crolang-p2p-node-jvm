package examples.ex_3;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.CrolangNode;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.ConnectionToNodeFailedReasonException;

public class Ex_3_Alice {
    public static void main(String[] args) throws ConnectToBrokerException, ConnectionToNodeFailedReasonException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        var bobCallbacks = SyncCrolangNodeCallbacks.builder()
                .onDisconnection(id -> {
                    System.out.println("Disconnected from Node " + id + " , trying to reconnect...");
                    try {
                        var secondAttemptNode = CrolangP2P.Java.connectToSingleNodeSync(Constants.BOB_ID);
                        System.out.println("Connected successfully to Node " + secondAttemptNode.getId());
                    } catch (ConnectionToNodeFailedReasonException e){
                        System.out.println("Error connecting to Node " + id + ": " + e.getMessage());
                    }
                })
                .build();

        CrolangNode node = CrolangP2P.Java.connectToSingleNodeSync(Constants.BOB_ID, bobCallbacks);

        System.out.println("Connected successfully to Node " + node.getId());
        node.send("CHANNEL_NUMBERS", "42");
        node.send("CHANNEL_DISCONNECT");
    }
}
