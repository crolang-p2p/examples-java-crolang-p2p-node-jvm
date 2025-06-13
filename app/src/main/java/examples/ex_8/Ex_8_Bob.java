package examples.ex_8;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.OnNewSocketMsgHandlersBuilder;
import org.crolangP2P.exceptions.ConnectToBrokerException;

import java.util.HashMap;
import java.util.Map;

public class Ex_8_Bob {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                OnNewSocketMsgHandlersBuilder.createNew()
                        .add("GREETINGS_CHANNEL", (fromId, msg) -> System.out.println("[GREETINGS_CHANNEL WebSocket][" + fromId + "]: " + msg))
                        .add("SECRET_CHANNEL", (fromId, msg) -> System.out.println("[SECRET_CHANNEL WebSocket][" + fromId + "]: " + msg))
                        .build()
        );

        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);
    }
}
