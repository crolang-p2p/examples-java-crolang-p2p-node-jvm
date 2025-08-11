package examples.ex_5;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;

public class Ex_5_Bob {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.BOB_ID,
                () -> System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID),
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
