package examples.ex_6;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.OutgoingCrolangNodeCallbacks;
import org.crolangP2P.CrolangNode;
import org.crolangP2P.java.OutgoingCrolangNodeCallbacksJava;

import java.util.HashMap;
import java.util.Map;

public class Ex_6_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    Map<String, OutgoingCrolangNodeCallbacks> targets = new HashMap<>();
                    targets.put(Constants.BOB_ID, OutgoingCrolangNodeCallbacksJava.builder().build());
                    targets.put(Constants.CAROL_ID, OutgoingCrolangNodeCallbacksJava.builder().build());
                    CrolangP2PJvm.Java.connectToMultipleNodes(
                            targets,
                            (successes, errors) -> {
                                CrolangP2PJvm.Java.getConnectedNode(
                                        Constants.BOB_ID,
                                        node -> node.ifPresent(n -> {
                                            System.out.println("Node " + Constants.BOB_ID + " is connected");
                                            n.sendString("GREETINGS_CHANNEL", "Hello " + n.getId() + "!");
                                        })
                                );

                                CrolangP2PJvm.Java.getAllConnectedNodes(nodes -> {
                                    CrolangNode carolNode = nodes.get(Constants.CAROL_ID);
                                    if (carolNode != null) {
                                        System.out.println("Node " + Constants.CAROL_ID + " is connected");
                                        carolNode.sendString("GREETINGS_CHANNEL", "Hello " + carolNode.getId() + "!");
                                    }
                                });
                            }
                    );
                },
                err -> System.err.println("Failed to connect to broker: " + err)
        );
    }
}
