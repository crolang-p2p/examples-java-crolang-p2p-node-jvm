package examples.ex_5;

import examples.Constants;
import org.crolangP2P.CrolangP2PJvm;

public class Ex_5_Alice {
    public static void main(String[] args) {
        CrolangP2PJvm.Java.connectToBroker(
                Constants.BROKER_ADDR,
                Constants.ALICE_ID,
                () -> {
                    System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

                    CrolangP2PJvm.Java.isRemoteNodeConnectedToBroker(
                            Constants.BOB_ID,
                            isConnected -> System.out.println("Is " + Constants.BOB_ID + " connected to the Broker: " + isConnected),
                            err -> System.out.println("Error checking connection to Broker for " + Constants.BOB_ID + ": " + err)
                    );

                    CrolangP2PJvm.Java.areRemoteNodesConnectedToBroker(
                            java.util.Set.of(Constants.BOB_ID, Constants.CAROL_ID),
                            statusMap -> statusMap.forEach((id, connected) ->
                                    System.out.println("Is " + id + " connected to the Broker: " + connected)
                            ),
                            err -> System.out.println("Error checking connection to Broker for multiple nodes: " + err)
                    );
                },
                err -> System.out.println("Failed to connect to Broker: " + err)
        );
    }
}
