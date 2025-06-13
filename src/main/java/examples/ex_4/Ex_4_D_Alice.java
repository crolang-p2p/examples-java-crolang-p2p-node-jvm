package examples.ex_4;

import examples.Constants;
import org.crolangP2P.AsyncCrolangNodeCallbacks;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;

import java.util.HashMap;
import java.util.Map;

public class Ex_4_D_Alice {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        AsyncCrolangNodeCallbacks bobCallbacks = AsyncCrolangNodeCallbacks.builder()
            .onConnectionSuccess(node -> {
                System.out.println("Connected to Node " + node.getId() + " successfully");
                node.send("GREETING", "Hello there!");
            })
            .onConnectionFailed((id, reason) -> {
                System.out.println("Failed to connect to Node " + id + ": " + reason);
            })
            .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                .add("CHANNEL_LETTERS", (id, msg) -> System.out.println("Received a message on CHANNEL_LETTERS from Node " + id + ": " + msg))
                .add("CHANNEL_NUMBERS", (id, msg) -> System.out.println("Received a message on CHANNEL_NUMBERS from Node " + id + ": " + msg))
                .build()
            )
            .build();

        AsyncCrolangNodeCallbacks carolCallbacks = AsyncCrolangNodeCallbacks.builder()
                .onConnectionSuccess(node -> {
                    System.out.println("Connected to Node " + node.getId() + " successfully");
                    node.send("GREETING", "Hello there!");
                })
                .onConnectionFailed((id, reason) -> {
                    System.out.println("Failed to connect to Node " + id + ": " + reason);
                })
                .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
                .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                        .add("ANIMALS_CHANNEL", (id, msg) -> System.out.println("Received a message on ANIMALS_CHANNEL from Node " + id + ": " + msg))
                        .build()
                )
                .build();

        Map<String, AsyncCrolangNodeCallbacks> callbacks = new HashMap<>();
        callbacks.put(Constants.BOB_ID, bobCallbacks);
        callbacks.put(Constants.CAROL_ID, carolCallbacks);

        CrolangP2P.Java.connectToMultipleNodesAsync(
                callbacks
        );
    }
}
