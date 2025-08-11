package examples.ex_7;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.OutgoingCrolangNodeCallbacks;
import org.crolangP2P.java.JavaOutgoingCrolangNodeCallbacks;

public class Ex_7_Alice {
    public static void main(String[] args) {
        final int COUNTER_THRESHOLD = 20;

        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    OutgoingCrolangNodeCallbacks callbacks = JavaOutgoingCrolangNodeCallbacks.builder()
                            .onDisconnection(id -> System.out.println("Disconnected from Node " + id))
                            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                                    .add("COUNT_CHANNEL", (node, msg) -> {
                                        System.out.println("[COUNT_CHANNEL][" + node.getId() + "]: " + msg);
                                        int i = Integer.parseInt(msg);
                                        if (i >= COUNTER_THRESHOLD) {
                                            System.out.println("Counter threshold exceeded, disconnecting from Node " + node.getId());
                                            node.disconnect();
                                        } else {
                                            node.send("COUNT_CHANNEL", Integer.toString(i + 1));
                                        }
                                    })
                                    .build()
                            )
                            .onConnectionSuccess(node -> {
                                System.out.println("Connected successfully to Node " + node.getId());
                                System.out.println("Disconnecting from Broker...");
                                CrolangP2PJvm.Java.disconnectFromBroker(
                                        () -> CrolangP2PJvm.Java.isLocalNodeConnectedToBroker(isLocalNodeConnectedToBroker ->
                                                System.out.println("Is local Node connected to the Broker: " + isLocalNodeConnectedToBroker)
                                        ),
                                        err -> System.err.println("Failed to disconnect from Broker: " + err)
                                );
                            })
                            .build();

                    CrolangP2PJvm.Java.connectToSingleNode(Constants.BOB_ID, callbacks);
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
