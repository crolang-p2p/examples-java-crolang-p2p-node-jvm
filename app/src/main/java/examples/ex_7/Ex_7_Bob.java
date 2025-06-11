package examples.ex_7;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_7_Bob {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.BOB_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

        IncomingCrolangNodesCallbacks callbacks = new IncomingCrolangNodesCallbacks.Builder()
                .onConnectionSuccess(node -> {
                    System.out.println("Connected successfully to Node " + node.getId());
                    System.out.println("Disconnecting from Broker...");
                    CrolangP2P.Java.disconnectFromBroker();
                    System.out.println("Is local Node connected to the Broker: " + CrolangP2P.Java.isLocalNodeConnectedToBroker());
                    node.send("COUNT_CHANNEL", "0");
                })
                .onDisconnection(id -> System.out.println("Disconnected from Node " + id))
                .onNewMsg(OnNewMsgHandlersBuilder.createNew()
                        .add("COUNT_CHANNEL", (node, msg) -> {
                            System.out.println("[COUNT_CHANNEL][" + node.getId() + "]: " + msg);
                            node.send("COUNT_CHANNEL", Integer.toString(Integer.parseInt(msg) + 1));
                        })
                        .build())
                .build();

        CrolangP2P.Java.allowIncomingConnections(callbacks);
    }
}
