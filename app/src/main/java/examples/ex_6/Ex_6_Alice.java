package examples.ex_6;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.CrolangNode;
import org.crolangP2P.exceptions.ConnectToBrokerException;

import java.util.HashMap;
import java.util.Map;

public class Ex_6_Alice {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        Map<String, SyncCrolangNodeCallbacks> targets = new HashMap<>();
        targets.put(Constants.BOB_ID, SyncCrolangNodeCallbacks.builder().build());
        targets.put(Constants.CAROL_ID, SyncCrolangNodeCallbacks.builder().build());
        CrolangP2P.Java.connectToMultipleNodesSync(targets);

        CrolangP2P.Java.getConnectedNode(Constants.BOB_ID).ifPresent(node -> {
            System.out.println("Node " + Constants.BOB_ID + " is connected");
            node.send("GREETINGS_CHANNEL", "Hello " + node.getId() + "!");
        });

        CrolangP2P.Java.getAllConnectedNodes().getOrDefault(Constants.CAROL_ID, null);
        CrolangNode carolNode = CrolangP2P.Java.getAllConnectedNodes().get(Constants.CAROL_ID);
        if (carolNode != null) {
            System.out.println("Node " + Constants.CAROL_ID + " is connected");
            carolNode.send("GREETINGS_CHANNEL", "Hello " + carolNode.getId() + "!");
        }
    }
}
