package examples.ex_5;

import examples.Constants;
import org.crolangP2P.CrolangP2P;
import org.crolangP2P.exceptions.RemoteNodesConnectionStatusCheckException;
import org.crolangP2P.exceptions.ConnectToBrokerException;

public class Ex_5_Alice {
    public static void main(String[] args) throws ConnectToBrokerException {
        CrolangP2P.Java.connectToBroker(Constants.BROKER_ADDR, Constants.ALICE_ID);
        System.out.println("Connected to Broker at " + Constants.BROKER_ADDR + " as " + Constants.ALICE_ID);

        try {
            boolean isBobConnected = CrolangP2P.Java.isRemoteNodeConnectedToBroker(Constants.BOB_ID);
            System.out.println("Is " + Constants.BOB_ID + " connected to the Broker: " + isBobConnected);
        } catch (RemoteNodesConnectionStatusCheckException e) {
            if (e instanceof RemoteNodesConnectionStatusCheckException.NotConnectedToBroker) {
                System.out.println("Local Node not connected to Broker");
            } else {
                System.out.println("Unknown error checking connection to Broker: " + e.getMessage());
            }
        }

        try {
            var statusMap = CrolangP2P.Java.areRemoteNodesConnectedToBroker(java.util.Set.of(Constants.BOB_ID, Constants.CAROL_ID));
            statusMap.forEach((id, connected) ->
                    System.out.println("Is " + id + " connected to the Broker: " + connected)
            );
        } catch (RemoteNodesConnectionStatusCheckException e) {
            System.out.println("Error checking connection to Broker: " + e.getMessage());
        }
    }
}
