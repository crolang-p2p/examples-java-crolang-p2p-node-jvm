package examples.ex_10;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_10_Bob {
    private static long startTime = 0;

    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.BOB_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

        var onNewMsgHandlers = OnNewP2PMsgHandlersBuilder.createNew()
            .add("LARGE_DATA_TRANSFER", (node, msg) -> {
                long duration = System.currentTimeMillis() - startTime;
                int bytes = msg.getBytes().length;
                System.out.println("Received " + bytes + " bytes of data on LARGE_DATA_TRANSFER from Node " + node.getId());
                System.out.println("Elapsed time since connection ready: " + duration + "ms (" + (duration > 0 ? (bytes / duration) : bytes) + " bytes/ms)");
            })
            .build();

        CrolangP2P.Java.allowIncomingConnections(
            new IncomingCrolangNodesCallbacks.Builder()
                .onConnectionSuccess(node -> {
                    startTime = System.currentTimeMillis();
                    System.out.println("Connected to Node " + node.getId() + " successfully, waiting for large data transfer...");
                })
                .onNewMsg(onNewMsgHandlers)
                .build()
        );
    }
}
