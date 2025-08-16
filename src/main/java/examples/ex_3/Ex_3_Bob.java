package examples.ex_3;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.IncomingCrolangNodesCallbacksJava;
import org.crolangP2P.java.OnNewP2PStringMsgHandlersBuilderJava;


public class Ex_3_Bob {

    static boolean isConnectionAttemptAuthorized(String id, String platform, String version) {
        System.out.println("Connection attempt from Node " + id + " on platform " + platform + " with version " + version);
        return id.equals(Constants.ALICE_ID);
    }

    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

                    var incomingCrolangNodesCallbacks = IncomingCrolangNodesCallbacksJava.builder()
                            .onConnectionAttempt(Ex_3_Bob::isConnectionAttemptAuthorized)
                            .onConnectionSuccess(node -> System.out.println("Connected successfully to Node " + node.getId()))
                            .onConnectionFailed((id, reason) -> System.out.println("Failed to connect to Node " + id + ": " + reason))
                            .onDisconnection(id -> System.out.println("Disconnected from node " + id))
                            .onNewStringMsg(OnNewP2PStringMsgHandlersBuilderJava.createNew()
                                .add("CHANNEL_NUMBERS", (node, msg) -> System.out.println("Received on CHANNEL_NUMBERS from " + node.getId() + ": " + msg))
                                .add("CHANNEL_DISCONNECT", (node, msg) -> {
                                    System.out.println("Received CHANNEL_DISCONNECT from " + node.getId() + ". Disconnecting...");
                                    CrolangP2PJvm.Java.stopIncomingConnections(() -> {
                                        System.out.println("Stopped incoming connections");
                                        CrolangP2PJvm.Java.areIncomingConnectionsAllowed(areIncomingConnectionsAllowed -> {
                                            System.out.println("Are incoming connections allowed: " + areIncomingConnectionsAllowed);
                                            System.out.println("Disconnecting from " + node.getId());
                                            node.disconnect();
                                        });
                                    });
                                })
                                .build()
                            )
                            .build();

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            incomingCrolangNodesCallbacks,
                            () -> System.out.println("Incoming connections are now allowed"),
                            error -> System.out.println("Error allowing incoming connections: " + error)
                    );
                },
                error -> System.out.println("Error connecting to Broker: " + error)
        );
    }

}
