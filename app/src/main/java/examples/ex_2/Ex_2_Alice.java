package examples.ex_2;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_2_Alice {
    public static void main(String[] args) {
        try {
            CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
            System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);
        } catch (ConnectToBrokerException e) {
            if (e instanceof ConnectToBrokerException.LocalClientAlreadyConnectedToBroker) {
                System.out.println("Local client is already connected to the Broker");
            } else if (e instanceof ConnectToBrokerException.ClientWithSameIdAlreadyConnected) {
                System.out.println("A client with the same ID " + Constants.ALICE_ID + " is already connected to the Broker");
            } else if (e instanceof ConnectToBrokerException.UnsupportedArchitecture) {
                System.out.println("This client version is not supported by the Broker");
            } else if (e instanceof ConnectToBrokerException.Unauthorized) {
                System.out.println("Unauthorized connection attempt to the Broker using ID " + Constants.ALICE_ID);
            } else if (e instanceof ConnectToBrokerException.SocketError) {
                System.out.println("Socket error while connecting to the Broker");
            } else if (e instanceof ConnectToBrokerException.ErrorParsingRTCConfiguration) {
                System.out.println("Error parsing RTC configuration sent by the Broker");
            } else if (e instanceof ConnectToBrokerException.UnknownError) {
                System.out.println("Unknown error while connecting to the Broker");
            }
        }
    }
}
