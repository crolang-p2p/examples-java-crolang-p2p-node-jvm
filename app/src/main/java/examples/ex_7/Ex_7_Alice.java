package examples.ex_7;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.OnNewMsgHandlersBuilder;
import org.crolangP2P.SyncCrolangNodeCallbacks;
import org.crolangP2P.CrolangNode;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.ConnectionToNodeFailedReasonException;

public class Ex_7_Alice {
    public static void main(String[] args) throws ConnectToBrokerException, ConnectionToNodeFailedReasonException {
        final int COUNTER_THRESHOLD = 20;

        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        SyncCrolangNodeCallbacks callbacks = SyncCrolangNodeCallbacks.builder()
                .onDisconnection(id -> System.out.println("Disconnected from Node " + id))
                .onNewMsg(OnNewMsgHandlersBuilder.createNew()
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
                .build();

        CrolangNode node = CrolangP2P.Java.connectToSingleNodeSync(Constants.BOB_ID, callbacks);
        System.out.println("Connected successfully to Node " + node.getId());
        System.out.println("Disconnecting from Broker...");
        CrolangP2P.Java.disconnectFromBroker();
        System.out.println("Is local Node connected to the Broker: " + CrolangP2P.Java.isLocalNodeConnectedToBroker());
    }
}
