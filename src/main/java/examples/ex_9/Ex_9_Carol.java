package examples.ex_9;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_9_Carol {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.CAROL_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.CAROL_ID);

        CrolangP2P.Java.allowIncomingConnections(
            new IncomingCrolangNodesCallbacks.Builder()
                .onConnectionSuccess(node -> {
                    System.out.println("Connected successfully to Node " + node.getId());
                    String msg = "Hello " + Constants.ALICE_ID + ", I'm Node " + Constants.CAROL_ID;
                    System.out.println("Sending message to Node " + node.getId() + ": " + msg);
                    node.send("REDIRECT_TO_ALICE", msg);
                })
                .build()
        );
    }
}
