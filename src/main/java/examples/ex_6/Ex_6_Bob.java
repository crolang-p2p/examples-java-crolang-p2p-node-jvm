package examples.ex_6;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.java.JavaIncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;

public class Ex_6_Bob {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

                    IncomingCrolangNodesCallbacks callbacks = JavaIncomingCrolangNodesCallbacks.builder()
                            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                                    .add("GREETINGS_CHANNEL", (node, msg) -> System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg))
                                    .build()
                            ).build();

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            callbacks,
                            () -> System.out.println("Incoming connections allowed"),
                            err -> System.err.println("Failed to allow incoming connections: " + err)
                    );
                },
                err -> System.err.println("Failed to connect to broker: " + err)
        );
    }
}
