package examples.ex_10;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.JavaIncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;

public class Ex_10_Bob {
    private static long startTime = 0;

    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

                    var onNewMsgHandlers = OnNewP2PMsgHandlersBuilder.createNew()
                            .add("LARGE_DATA_TRANSFER", (node, msg) -> {
                                long duration = System.currentTimeMillis() - startTime;
                                int bytes = msg.getBytes().length;
                                System.out.println("Received " + bytes + " bytes of data on LARGE_DATA_TRANSFER from Node " + node.getId());
                                System.out.println("Elapsed time since connection ready: " + duration + "ms (" + (duration > 0 ? (bytes / duration) : bytes) + " bytes/ms)");
                            })
                            .build();

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            JavaIncomingCrolangNodesCallbacks.builder()
                                    .onConnectionSuccess(node -> {
                                        startTime = System.currentTimeMillis();
                                        System.out.println("Connected to Node " + node.getId() + " successfully, waiting for large data transfer...");
                                    })
                                    .onNewMsg(onNewMsgHandlers)
                                    .build(),
                            () -> System.out.println("Incoming connections allowed"),
                            err -> System.err.println("Failed to allow incoming connections: " + err)
                    );
                },
                err -> System.err.println("Failed to connect to broker: " + err)
        );
    }
}
