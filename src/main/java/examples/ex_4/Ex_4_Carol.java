package examples.ex_4;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.IncomingCrolangNodesCallbacksJava;
import org.crolangP2P.java.OnNewP2PStringMsgHandlersBuilderJava;

public class Ex_4_Carol {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.CAROL_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.CAROL_ID);
                    CrolangP2PJvm.Java.allowIncomingConnections(
                            IncomingCrolangNodesCallbacksJava.builder()
                                .onConnectionSuccess(node -> {
                                    System.out.println("Connected to Node " + node.getId() + " successfully");
                                    node.sendString("CHANNEL_ANIMALS", "Unicorns");
                                })
                                .onNewStringMsg(OnNewP2PStringMsgHandlersBuilderJava.createNew()
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
