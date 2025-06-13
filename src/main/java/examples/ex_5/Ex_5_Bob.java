package examples.ex_5;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_5_Bob {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.BOB_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.BOB_ID);
    }
}
