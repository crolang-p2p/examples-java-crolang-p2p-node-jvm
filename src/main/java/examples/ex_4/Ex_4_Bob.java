package examples.ex_4;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_4_Bob {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.BOB_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);
        CrolangP2P.Java.allowIncomingConnections(IncomingCrolangNodesCallbacks.builder()
                .onConnectionSuccess(node -> {
                    System.out.println("Connected to Node " + node.getId() + " successfully");
                    node.send("CHANNEL_ANIMALS", "Unicorns");
                    node.send("CHANNEL_NUMBERS", "42");
                })
                .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                        .add("GREETINGS_CHANNEL", (node, msg) -> {
                            System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg);
                        })
                        .build()
                )
                .build()
        );
    }
}
