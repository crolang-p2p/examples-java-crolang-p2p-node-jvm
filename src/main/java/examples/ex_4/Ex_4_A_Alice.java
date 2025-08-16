package examples.ex_4;

import examples.Constants;
import org.crolangP2P.*;
import org.crolangP2P.java.OnNewP2PStringMsgHandlersBuilderJava;
import org.crolangP2P.java.OutgoingCrolangNodeCallbacksJava;

public class Ex_4_A_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    OutgoingCrolangNodeCallbacks callbacks = OutgoingCrolangNodeCallbacksJava.builder()
                            .onConnectionSuccess(node -> {
                                System.out.println("Connected to Node " + node.getId() + " successfully");
                                node.sendString("GREETING", "Hello there!");
                            })
                            .onConnectionFailed((id, reason) -> {
                                System.out.println("Failed to connect to Node " + id + ": " + reason);
                            })
                            .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
                            .onNewStringMsg(OnNewP2PStringMsgHandlersBuilderJava.createNew()
                                    .add("CHANNEL_LETTERS", (node, msg) -> System.out.println("Received a message on CHANNEL_LETTERS from Node " + node.getId() + ": " + msg))
                                    .add("CHANNEL_NUMBERS", (node, msg) -> System.out.println("Received a message on CHANNEL_NUMBERS from Node " + node.getId() + ": " + msg))
                                    .build()
                            )
                            .build();

                    CrolangP2PJvm.Java.connectToSingleNode(Constants.BOB_ID, callbacks);
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
