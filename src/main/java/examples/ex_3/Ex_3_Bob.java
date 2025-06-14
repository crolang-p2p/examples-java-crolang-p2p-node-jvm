package examples.ex_3;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;


public class Ex_3_Bob {

    static boolean isConnectionAttemptAuthorized(String id, String platform, String version) {
        System.out.println("Connection attempt from Node " + id + " on platform " + platform + " with version " + version);
        return id.equals(Constants.ALICE_ID);
    }

    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.BOB_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

        var incomingCrolangNodesCallbacks = new IncomingCrolangNodesCallbacks.Builder()
            .onConnectionAttempt(Ex_3_Bob::isConnectionAttemptAuthorized)
            .onConnectionSuccess(node -> System.out.println("Connected successfully to Node " + node.getId()))
            .onConnectionFailed((id, reason) -> System.out.println("Failed to connect to Node " + id + ": " + reason))
            .onDisconnection(id -> System.out.println("Disconnected from node " + id))
            .onNewMsg(
                OnNewP2PMsgHandlersBuilder.createNew()
                    .add("CHANNEL_NUMBERS", (node, msg) -> System.out.println("Received on CHANNEL_NUMBERS from " + node.getId() + ": " + msg))
                    .add("CHANNEL_DISCONNECT", (node, msg) -> {
                        System.out.println("Received CHANNEL_DISCONNECT from " + node.getId() + ". Disconnecting...");
                        node.disconnect();
                    })
                    .build()
            )
            .build();

        CrolangP2P.Java.allowIncomingConnections(incomingCrolangNodesCallbacks);
    }

}
