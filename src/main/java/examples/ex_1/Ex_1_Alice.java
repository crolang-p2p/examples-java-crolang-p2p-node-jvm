package examples.ex_1;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.java.JavaOutgoingCrolangNodeCallbacks;

public class Ex_1_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    var onNewMsgHandlers = OnNewP2PMsgHandlersBuilder.createNew()
                            .add("GREETINGS_CHANNEL", (node, msg) -> {
                                System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg);
                                node.send("GREETING_CHANNEL", "Hi " + node.getId() + ", I'm Node " + Constants.ALICE_ID);
                            })
                            .build();

                    CrolangP2PJvm.Java.connectToSingleNode(
                            Constants.BOB_ID,
                            JavaOutgoingCrolangNodeCallbacks.builder()
                                    .onNewMsg(onNewMsgHandlers)
                                    .onConnectionSuccess(node -> {
                                        System.out.println("Connected successfully to Node " + node.getId() + ", platform: " + node.getPlatform() + ", version: " + node.getVersion());
                                        node.send("GREETINGS_CHANNEL", "Hello from Node " + Constants.ALICE_ID);
                                    })
                                    .build()
                    );
                },
                error -> System.out.println("Error connecting to Broker: " + error)
        );
    }
}
