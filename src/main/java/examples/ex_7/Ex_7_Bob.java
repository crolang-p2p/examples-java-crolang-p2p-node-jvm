package examples.ex_7;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.java.JavaIncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;

public class Ex_7_Bob {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

                    IncomingCrolangNodesCallbacks callbacks = JavaIncomingCrolangNodesCallbacks.builder()
                            .onConnectionSuccess(node -> {
                                System.out.println("Connected successfully to Node " + node.getId());
                                System.out.println("Disconnecting from Broker...");
                                CrolangP2PJvm.Java.disconnectFromBroker(
                                        () -> CrolangP2PJvm.Java.isLocalNodeConnectedToBroker(isLocalNodeConnected -> {
                                            System.out.println("Is local Node connected to the Broker: " + isLocalNodeConnected);
                                            node.send("COUNT_CHANNEL", "0");
                                        }),
                                        err -> System.err.println("Failed to disconnect from Broker: " + err)
                                );
                            })
                            .onDisconnection(id -> System.out.println("Disconnected from Node " + id))
                            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                                    .add("COUNT_CHANNEL", (node, msg) -> {
                                        System.out.println("[COUNT_CHANNEL][" + node.getId() + "]: " + msg);
                                        node.send("COUNT_CHANNEL", Integer.toString(Integer.parseInt(msg) + 1));
                                    })
                                    .build())
                            .build();

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            callbacks,
                            () -> System.out.println("Incoming connections are allowed")
                    );
                },
                err -> System.err.println("Failed to connect to Broker: " + err)
        );
    }
}
