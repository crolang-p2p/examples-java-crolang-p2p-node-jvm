package examples.ex_4;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.JavaIncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;

public class Ex_4_Carol {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.CAROL_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.CAROL_ID);
                    CrolangP2PJvm.Java.allowIncomingConnections(
                            JavaIncomingCrolangNodesCallbacks.builder()
                                .onConnectionSuccess(node -> {
                                    System.out.println("Connected to Node " + node.getId() + " successfully");
                                    node.send("CHANNEL_ANIMALS", "Unicorns");
                                })
                                .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                                        .add("GREETINGS_CHANNEL", (node, msg) -> {
                                            System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg);
                                        })
                                        .build()
                                )
                                .build(),
                            () -> System.out.println("Incoming connections allowed"),
                            err -> System.out.println("Failed to allow incoming connections: " + err)
                    );
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
