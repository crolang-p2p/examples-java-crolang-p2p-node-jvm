package examples.ex_12;

import com.google.gson.Gson;
import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;

public class Ex_12_Alice {

    private static class Authentication {

        private final String token;
        private final String password;

        Authentication(String token, String password) {
            this.token = token;
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        public String getPassword() {
            return password;
        }

    }

    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID),
                err -> System.out.println("Failed to connect to Broker: " + err),
                new Gson().toJson(new Authentication("magic-token", "unicorns"))
        );
    }
}
