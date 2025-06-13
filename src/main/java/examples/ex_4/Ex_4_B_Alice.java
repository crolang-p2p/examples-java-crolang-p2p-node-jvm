package examples.ex_4;

import examples.Constants;
import org.crolangP2P.CrolangNodeConnectionResult;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.exceptions.ConnectToBrokerException;

import java.util.HashMap;
import java.util.Map;

public class Ex_4_B_Alice {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        SyncCrolangNodeCallbacks bobCallbacks = SyncCrolangNodeCallbacks.builder()
            .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                .add("CHANNEL_LETTERS", (id, msg) -> System.out.println("Received a message on CHANNEL_LETTERS from Node " + id + ": " + msg))
                .add("CHANNEL_NUMBERS", (id, msg) -> System.out.println("Received a message on CHANNEL_NUMBERS from Node " + id + ": " + msg))
                .build()
            )
            .build();
        SyncCrolangNodeCallbacks carolCallbacks = SyncCrolangNodeCallbacks.builder()
            .onDisconnection(id -> System.out.println("Node " + id + " disconnected"))
            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                .add("CHANNEL_ANIMALS", (id, msg) -> System.out.println("Received a message on CHANNEL_ANIMALS from Node " + id + ": " + msg))
                .build()
            )
            .build();

        Map<String, SyncCrolangNodeCallbacks> callbacksMap = new HashMap<>();
        callbacksMap.put(Constants.BOB_ID, bobCallbacks);
        callbacksMap.put(Constants.CAROL_ID, carolCallbacks);

        Map<String, CrolangNodeConnectionResult> results = CrolangP2P.Java.connectToMultipleNodesSync(callbacksMap);
        results.forEach((nodeId, result) -> {
            if(result.getNode().isPresent()){
                System.out.println("Connected to Node " + nodeId + " successfully");
                result.getNode().get().send("GREETING", "Hello there!");
            } else {
                System.out.println("Failed to connect to Node " + nodeId + ": " + result.getException().get());
            }
        });
    }
}
