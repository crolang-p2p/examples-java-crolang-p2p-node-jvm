package examples.ex_3;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.OutgoingCrolangNodeCallbacksJava;

public class Ex_3_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    CrolangP2PJvm.Java.connectToSingleNode(Constants.BOB_ID, OutgoingCrolangNodeCallbacksJava.builder()
                            .onDisconnection(id -> {
                                System.out.println("Disconnected from Node " + id + " , trying to reconnect...");
                                CrolangP2PJvm.Java.connectToSingleNode(
                                        Constants.BOB_ID,
                                        OutgoingCrolangNodeCallbacksJava.builder()
                                                .onConnectionSuccess(secondAttemptNode -> System.out.println("Connected successfully to Node " + secondAttemptNode.getId()))
                                                .onConnectionFailed((connectionFailedId, err) -> System.out.println("Error connecting to Node " + connectionFailedId + ": " + err))
                                                .build()
                                );
                            })
                            .onConnectionSuccess(node -> {
                                System.out.println("Connected successfully to Node " + node.getId());
                                node.sendString("CHANNEL_NUMBERS", "42");
                                node.sendString("CHANNEL_DISCONNECT", "");
                            })
                            .build());
                },
                error -> System.out.println("Error connecting to Broker: " + error)
        );
    }
}
