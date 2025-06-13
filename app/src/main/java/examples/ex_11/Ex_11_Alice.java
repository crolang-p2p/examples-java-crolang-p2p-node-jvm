package examples.ex_11;

import examples.Constants;
import org.crolangP2P.*;
import org.crolangP2P.exceptions.ConnectToBrokerException;

import java.util.Optional;

public class Ex_11_Alice {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID, BrokerConnectionAdditionalParameters.builder()
            .logging(LoggingOptions.builder()
                .enableBaseLogging(true) //DEFAULT: false
                .enableDebugLogging(true) //DEFAULT: false
                .build()
            )
            .lifecycleCallbacks(BrokerLifecycleCallbacks.builder()
                .onInvoluntaryDisconnection(cause -> System.out.println("Involuntary disconnection from Broker: " + cause)) //DEFAULT: does nothing
                .onReconnectionAttempt(() -> System.out.println("Attempting to reconnect to Broker")) //DEFAULT: does nothing
                .onSuccessfullyReconnected(() -> System.out.println("Successfully reconnected to Broker")) //DEFAULT: does nothing
                .build()
            )
            .settings(CrolangSettings.builder()
                .p2pConnectionTimeoutMillis(5000) //DEFAULT: 30000
                .multipartP2PMessageTimeoutMillis(1000) //DEFAULT: 60000
                .reconnection(true) //DEFAULT: true
                .maxReconnectionAttempts(Optional.empty()) //DEFAULT: Optional.empty()
                .reconnectionAttemptsDeltaMs(500) //DEFAULT: 2000
                .build()
            )
            .build()
        );

        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);
    }
}
