package examples.ex_11;

import examples.Constants;
import org.crolangP2P.*;
import org.crolangP2P.java.BrokerConnectionAdditionalParametersJava;
import org.crolangP2P.java.BrokerLifecycleCallbacksJava;
import org.crolangP2P.java.CrolangSettingsJava;
import org.crolangP2P.java.LoggingOptionsJava;

import java.util.Optional;

public class Ex_11_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID),
                err -> System.out.println("Failed to connect to Broker: " + err),
                BrokerConnectionAdditionalParametersJava.builder()
                    .logging(LoggingOptionsJava.builder()
                        .enableBaseLogging(true) //DEFAULT: false
                        .enableDebugLogging(true) //DEFAULT: false
                        .build()
                    )
                    .lifecycleCallbacks(BrokerLifecycleCallbacksJava.builder()
                        .onInvoluntaryDisconnection(cause -> System.out.println("Involuntary disconnection from Broker: " + cause)) //DEFAULT: does nothing
                        .onReconnectionAttempt(() -> System.out.println("Attempting to reconnect to Broker")) //DEFAULT: does nothing
                        .onSuccessfullyReconnected(() -> System.out.println("Successfully reconnected to Broker")) //DEFAULT: does nothing
                        .build()
                    )
                    .settings(CrolangSettingsJava.builder()
                        .p2pConnectionTimeoutMillis(5000) //DEFAULT: 30000
                        .multipartP2PMessageTimeoutMillis(1000) //DEFAULT: 60000
                        .reconnection(true) //DEFAULT: true
                        .maxReconnectionAttempts(Optional.empty()) //DEFAULT: Optional.empty()
                        .reconnectionAttemptsDeltaMs(500) //DEFAULT: 2000
                        .build()
                    )
                    .build()
        );
    }
}
