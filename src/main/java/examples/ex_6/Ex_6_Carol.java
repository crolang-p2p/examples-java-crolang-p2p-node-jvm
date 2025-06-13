package examples.ex_6;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.IncomingCrolangNodesCallbacks;
import org.crolangP2P.OnNewP2PMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_6_Carol {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.CAROL_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.CAROL_ID);

        IncomingCrolangNodesCallbacks callbacks = new IncomingCrolangNodesCallbacks.Builder()
            .onNewMsg(OnNewP2PMsgHandlersBuilder.createNew()
                .add("GREETINGS_CHANNEL", (node, msg) -> System.out.println("Received a message on GREETINGS_CHANNEL from Node " + node.getId() + ": " + msg))
                .build()
            ).build();

        CrolangP2P.Java.allowIncomingConnections(callbacks);
    }
}
