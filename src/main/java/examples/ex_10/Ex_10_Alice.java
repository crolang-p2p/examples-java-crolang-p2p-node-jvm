package examples.ex_10;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;
import org.crolangP2P.java.OutgoingCrolangNodeCallbacksJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Objects;

public class Ex_10_Alice {
    public static void main(String[] args) throws IOException {
        String resourcePath = "/large_file.txt"; // ~100 MB file in resources
        System.out.println("Reading large file...");
        byte[] content;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Ex_10_Alice.class.getResourceAsStream(resourcePath))
        ))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            content = sb.toString().getBytes();
        }
        System.out.println("File read successfully. Bytes: " + content.length);

        byte[] toSend = new byte[content.length * 10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(content, 0, toSend, i * content.length, content.length);
        }

        System.out.println("Bytes to send: " + toSend.length);
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);
                    CrolangP2PJvm.Java.connectToSingleNode(
                            Constants.BOB_ID,
                            OutgoingCrolangNodeCallbacksJava.builder()
                                    .onConnectionSuccess(node -> {
                                        System.out.println("Connected to Node " + node.getId() + " successfully");
                                        System.out.println("Sending large data to Node " + node.getId() + "...");
                                        var sendResult = node.sendBytes("LARGE_DATA_TRANSFER", toSend);
                                        System.out.println("Data sent result: " + sendResult);
                                    })
                                    .build()
                    );
                },
                err -> System.err.println("Failed to connect to broker: " + err)
        );
    }
}
