package examples.ex_9;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.JavaOutgoingCrolangNodeCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;

public class Ex_9_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    var onNewMsgHandlers = OnNewP2PMsgHandlersBuilder.createNew()
                            .add("REDIRECT_TO_ALICE", (node, msg) -> System.out.println("[REDIRECT_TO_ALICE][" + node.getId() + "]: " + msg))
                            .build();

                    CrolangP2PJvm.Java.connectToSingleNode(
                            Constants.BOB_ID,
                            JavaOutgoingCrolangNodeCallbacks.builder()
                                    .onNewMsg(onNewMsgHandlers)
                                    .onConnectionSuccess(n -> {
                                        System.out.println("Connected successfully to Node " + Constants.BOB_ID);
                                        n.send("CONNECT_TO_CAROL", "");
                                    })
                                    .build()
                    );
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
