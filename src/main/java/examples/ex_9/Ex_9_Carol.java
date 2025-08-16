package examples.ex_9;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.IncomingCrolangNodesCallbacksJava;

public class Ex_9_Carol {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.CAROL_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.CAROL_ID);

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            IncomingCrolangNodesCallbacksJava.builder()
                                    .onConnectionSuccess(node -> {
                                        System.out.println("Connected successfully to Node " + node.getId());
                                        String msg = "Hello " + Constants.ALICE_ID + ", I'm Node " + Constants.CAROL_ID;
                                        System.out.println("Sending message to Node " + node.getId() + ": " + msg);
                                        node.sendString("REDIRECT_TO_ALICE", msg);
                                    })
                                    .build(),
                            () -> System.out.println("Incoming connections are now allowed"),
                            err -> System.err.println("Failed to allow incoming connections: " + err)
                    );
                },
                err -> System.err.println("Failed to connect to Broker: " + err)
        );
    }
}
