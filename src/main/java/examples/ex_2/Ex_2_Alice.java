package examples.ex_2;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;

public class Ex_2_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID),
                error -> {
                    switch (error){
                        case LOCAL_CLIENT_ALREADY_CONNECTED:
                            System.out.println("Local client is already connected to the Broker");
                            break;
                        case ALREADY_PERFORMING_CONNECTION:
                            System.out.println("Already performing a connection to the Broker");
                            break;
                        case CLIENT_WITH_SAME_ID_ALREADY_CONNECTED:
                            System.out.println("A client with the same ID " + Constants.ALICE_ID + " is already connected to the Broker");
                            break;
                        case UNSUPPORTED_ARCHITECTURE:
                            System.out.println("This client version is not supported by the Broker");
                            break;
                        case UNAUTHORIZED:
                            System.out.println("Unauthorized connection attempt to the Broker using ID " + Constants.ALICE_ID);
                            break;
                        case SOCKET_ERROR:
                            System.out.println("Socket error while connecting to the Broker");
                            break;
                        case ERROR_PARSING_RTC_CONFIGURATION:
                            System.out.println("Error parsing RTC configuration sent by the Broker");
                            break;
                        case UNKNOWN_ERROR:
                            System.out.println("Unknown error while connecting to the Broker");
                            break;
                    }
                }
        );
    }
}
