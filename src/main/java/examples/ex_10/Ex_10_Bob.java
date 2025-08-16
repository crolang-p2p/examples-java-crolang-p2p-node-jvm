package examples.ex_10;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.IncomingByteArrayMsgCallbacksBuilderJava;
import org.crolangP2P.java.IncomingCrolangNodesCallbacksJava;
import org.crolangP2P.java.OnNewP2PByteArrayMsgHandlersBuilderJava;

public class Ex_10_Bob {
    private static long startTime = 0;

    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

                    var onNewMsgHandlers = OnNewP2PByteArrayMsgHandlersBuilderJava.createNew()
                            .add("LARGE_DATA_TRANSFER", IncomingByteArrayMsgCallbacksBuilderJava.createNew()
                                    .onNewMsgPartReceived((node, msgId, part, total) -> {
                                        Double percentage = (part.doubleValue() / total) * 100;
                                        System.out.printf("[msgId: %s] Received byte array msg part %d/%d from Node %s (%.3f%%)%n", msgId, part, total, node.getId(), percentage);
                                    })
                                    .onNewCompleteMsgReceived((node, msgId, msg) -> {
                                        long duration = System.currentTimeMillis() - startTime;
                                        int bytes = msg.length;
                                        System.out.printf("[msgId: %s] Received complete byte array msg of %d bytes from Node %s%n", msgId, bytes, node.getId());
                                        System.out.printf("Elapsed time since connection ready: %d ms (%d bytes/ms)%n", duration, bytes / (duration > 0 ? duration : 1));
                                    })
                                    .onMsgCorruption((node, msgId) -> {
                                        System.out.printf("[msgId: %s] Corruption detected on byte array msg from Node %s%n", msgId, node.getId());
                                    })
                                    .build()
                            )
                            .build();

                    CrolangP2PJvm.Java.allowIncomingConnections(
                            IncomingCrolangNodesCallbacksJava.builder()
                                    .onConnectionSuccess(node -> {
                                        startTime = System.currentTimeMillis();
                                        System.out.println("Connected to Node " + node.getId() + " successfully, waiting for large data transfer...");
                                    })
                                    .onNewByteArrayMsg(onNewMsgHandlers)
                                    .build(),
                            () -> System.out.println("Incoming connections allowed"),
                            err -> System.err.println("Failed to allow incoming connections: " + err)
                    );
                },
                err -> System.err.println("Failed to connect to broker: " + err)
        );
    }
}
