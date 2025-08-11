package examples.ex_8;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;

public class Ex_8_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    CrolangP2PJvm.Java.sendSocketMsg(
                            Constants.BOB_ID,
                            "GREETINGS_CHANNEL",
                            "Hello from " + Constants.ALICE_ID + "!",
                            () -> System.out.println("Message sent successfully to Broker to be relayed to " + Constants.BOB_ID + " on GREETINGS_CHANNEL"),
                            err -> {
                                switch (err){
                                    case TRIED_TO_SEND_MSG_TO_SELF:
                                        System.out.println("Error: Tried to send a message to myself. This is not allowed.");
                                        break;
                                    case EMPTY_ID:
                                        System.out.println("Error: The ID to send the message to is empty.");
                                        break;
                                    case EMPTY_CHANNEL:
                                        System.out.println("Error: The channel to send the message to is empty.");
                                        break;
                                    case NOT_CONNECTED_TO_BROKER:
                                        System.out.println("Error: Not connected to the broker. Please connect first.");
                                        break;
                                    case DISABLED:
                                        System.out.println("Error: Sending messages through WebSocket is disabled on the Broker.");
                                        break;
                                    case REMOTE_NODE_NOT_CONNECTED_TO_BROKER:
                                        System.out.println("Error: The remote node is not connected to the broker.");
                                        break;
                                    case UNAUTHORIZED_TO_CONTACT_REMOTE_NODE:
                                        System.out.println("Error: Unauthorized to contact the remote node. Check permissions.");
                                        break;
                                    case UNKNOWN_ERROR:
                                        System.out.println("Error: An unknown error occurred while sending the message.");
                                        break;
                                }
                            }
                    );

                    CrolangP2PJvm.Java.sendSocketMsg(Constants.BOB_ID, "SECRET_CHANNEL", "42");
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
