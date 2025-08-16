package examples.ex_4;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.OutgoingCrolangNodeCallbacks;
import org.crolangP2P.java.OnNewP2PStringMsgHandlersBuilderJava;
import org.crolangP2P.java.OutgoingCrolangNodeCallbacksJava;

import java.util.HashMap;
import java.util.Map;

public class Ex_4_B_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    OutgoingCrolangNodeCallbacks bobCallbacks = OutgoingCrolangNodeCallbacksJava.builder()
                            .onConnectionSuccess(node -> {
                                System.out.println("Connected to Node " + node.getId() + " successfully");
                                node.sendString("GREETING", "Hello there!");
                            })
                            .onConnectionFailed((id, reason) -> System.out.println("Failed to connect to Node " + id + ": " + reason))
                            .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
                            .onNewStringMsg(OnNewP2PStringMsgHandlersBuilderJava.createNew()
                                    .add("CHANNEL_LETTERS", (node, msg) -> System.out.println("Received a message on CHANNEL_LETTERS from Node " + node.getId() + ": " + msg))
                                    .add("CHANNEL_NUMBERS", (node, msg) -> System.out.println("Received a message on CHANNEL_NUMBERS from Node " + node.getId() + ": " + msg))
                                    .build()
                            )
                            .build();

                    OutgoingCrolangNodeCallbacks carolCallbacks = OutgoingCrolangNodeCallbacksJava.builder()
                            .onConnectionSuccess(node -> {
                                System.out.println("Connected to Node " + node.getId() + " successfully");
                                node.sendString("GREETING", "Hello there!");
                            })
                            .onConnectionFailed((id, reason) -> System.out.println("Failed to connect to Node " + id + ": " + reason))
                            .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
                            .onNewStringMsg(OnNewP2PStringMsgHandlersBuilderJava.createNew()
                                    .add("CHANNEL_ANIMALS", (node, msg) -> System.out.println("Received a message on ANIMALS_CHANNEL from Node " + node.getId() + ": " + msg))
                                    .build()
                            )
                            .build();

                    Map<String, OutgoingCrolangNodeCallbacks> callbacks = new HashMap<>();
                    callbacks.put(Constants.BOB_ID, bobCallbacks);
                    callbacks.put(Constants.CAROL_ID, carolCallbacks);

                    CrolangP2PJvm.Java.connectToMultipleNodes(callbacks);
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
