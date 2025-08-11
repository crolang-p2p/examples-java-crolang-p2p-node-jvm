package examples.ex_1;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.JavaIncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;

public class Ex_1_Bob {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

                    var onNewMsgHandlers = OnNewP2PMsgHandlersBuilder.createNew()
                            .add("GREETINGS_CHANNEL", (node, msg) -> {
                                System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg);
                                node.send("GREETINGS_CHANNEL", "Hi " + node.getId() + ", I'm Node " + Constants.BOB_ID);
                            })
                            .build();

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            JavaIncomingCrolangNodesCallbacks.builder()
                                    .onConnectionSuccess(node -> System.out.println("Connected successfully to Node " + node.getId() + ", platform: " + node.getPlatform() + ", version: " + node.getVersion()))
                                    .onNewMsg(onNewMsgHandlers)
                                    .build(),
                            () -> System.out.println("Incoming connections are now allowed"),
                            error -> System.out.println("Error allowing incoming connections: " + error)
                    );
                },
                error -> System.out.println("Error connecting to Broker: " + error)
        );
    }
}
