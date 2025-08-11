package examples.ex_8;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.OnNewSocketMsgHandlersBuilder;

public class Ex_8_Bob {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID),
                err -> System.out.println("Failed to connect to Broker: " + err),
                OnNewSocketMsgHandlersBuilder.createNew()
                        .add("GREETINGS_CHANNEL", (fromId, msg) -> System.out.println("[GREETINGS_CHANNEL WebSocket][" + fromId + "]: " + msg))
                        .add("SECRET_CHANNEL", (fromId, msg) -> System.out.println("[SECRET_CHANNEL WebSocket][" + fromId + "]: " + msg))
                        .build()
        );
    }
}
