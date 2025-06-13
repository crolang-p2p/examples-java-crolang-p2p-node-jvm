package examples.ex_8;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.SendSocketMsgException;

public class Ex_8_Alice {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        try {
            CrolangP2P.Java.sendSocketMsg(Constants.BOB_ID, "GREETINGS_CHANNEL", "Hello from " + Constants.ALICE_ID + "!");

            CrolangP2P.Java.sendSocketMsg(Constants.BOB_ID, "SECRET_CHANNEL", "42");
        } catch (SendSocketMsgException error) {
            if (error instanceof SendSocketMsgException.TriedToSendMsgToSelf) {
                System.out.println("Error: Tried to send a message to myself. This is not allowed.");
            } else if (error instanceof SendSocketMsgException.EmptyId) {
                System.out.println("Error: The ID to send the message to is empty.");
            } else if (error instanceof SendSocketMsgException.EmptyChannel) {
                System.out.println("Error: The channel to send the message to is empty.");
            } else if (error instanceof SendSocketMsgException.NotConnectedToBroker) {
                System.out.println("Error: Not connected to the broker. Please connect first.");
            } else if (error instanceof SendSocketMsgException.Disabled) {
                System.out.println("Error: Sending messages through WebSocket is disabled on the Broker.");
            } else if (error instanceof SendSocketMsgException.RemoteNodeNotConnectedToBroker) {
                System.out.println("Error: The remote node is not connected to the broker.");
            } else if (error instanceof SendSocketMsgException.UnauthorizedToContactRemoteNode) {
                System.out.println("Error: Unauthorized to contact the remote node. Check permissions.");
            } else if (error instanceof SendSocketMsgException.UnknownError) {
                System.out.println("Error: An unknown error occurred while sending the message.");
            }
        }
    }
}
