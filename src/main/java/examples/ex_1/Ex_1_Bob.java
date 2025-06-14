package examples.ex_1;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_1_Bob {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.BOB_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);

        var onNewMsgHandlers = OnNewP2PMsgHandlersBuilder.createNew()
                .add("GREETINGS_CHANNEL", (node, msg) -> {
                    System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg);
                    node.send("GREETINGS_CHANNEL", "Hi " + node.getId() + ", I'm Node " + Constants.BOB_ID);
                })
                .build();

        CrolangP2P.Java.allowIncomingConnections(
                new IncomingCrolangNodesCallbacks.Builder()
                        .onConnectionSuccess(node -> System.out.println("Connected successfully to Node " + node.getId() + ", platform: " + node.getPlatform() + ", version: " + node.getVersion()))
                        .onNewMsg(onNewMsgHandlers)
                        .build()
        );
        System.out.println("Incoming connections are now allowed");
    }
}
