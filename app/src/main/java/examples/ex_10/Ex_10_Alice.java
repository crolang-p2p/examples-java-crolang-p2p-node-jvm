package examples.ex_10;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.exceptions.ConnectToBrokerException;
import org.crolangP2P.exceptions.ConnectionToNodeFailedReasonException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Objects;

public class Ex_10_Alice {
    public static void main(String[] args) throws IOException, ConnectionToNodeFailedReasonException, ConnectToBrokerException {
        String resourcePath = "/large_file.txt"; // ~100 MB file in resources
        System.out.println("Reading large file...");
        String content;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Ex_10_Alice.class.getResourceAsStream(resourcePath))
        ))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            content = sb.toString();
        }
        System.out.println("File read successfully. Bytes: " + content.getBytes().length);
        StringBuilder toSend = new StringBuilder();
        toSend.append(content.repeat(10));

        System.out.println("Bytes to send: " + toSend.toString().getBytes().length);
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);
        var node = CrolangP2P.Java.connectToSingleNodeSync(Constants.BOB_ID);
        System.out.println("Connected to Node " + node.getId() + " successfully");
        System.out.println("Sending large data to Node " + node.getId() + "...");
        var sendResult = node.send("LARGE_DATA_TRANSFER", toSend.toString());
        System.out.println("Data sent result: " + sendResult);
    }
}
