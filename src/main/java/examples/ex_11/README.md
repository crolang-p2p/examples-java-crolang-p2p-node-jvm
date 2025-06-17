# Example 11: Customizing Local Node Behavior with BrokerConnectionAdditionalParameters
## Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Parameter Details](#parameter-details)
- [Expected Output](#expected-output)
- [Running the Example](#running-the-example)

## Learning Objectives

This example shows how to:
- Use `BrokerConnectionAdditionalParameters` to customize the behavior of your node when connecting to the Broker.
- Enable and configure advanced logging options for debugging and monitoring.
- Handle broker lifecycle events such as involuntary disconnections and reconnection attempts.
- Fine-tune connection and reconnection settings (timeouts, retry logic, etc.) using `CrolangSettings`.

## Involved Files

- Ex_11_Alice.java: Alice connects to the Broker with custom parameters for logging, lifecycle callbacks, and connection settings.
- Constants.java: Common constants (IDs, broker address).

## Example Overview

- Alice connects to the Broker using a highly customized set of parameters:
    - **LoggingOptions**: Enables both base and debug logging for detailed output.
    - **BrokerLifecycleCallbacks**: Handles events such as involuntary disconnection, reconnection attempts, and successful reconnections, printing informative messages for each.
    - **CrolangSettings**: Adjusts timeouts for P2P connections and multipart messages, sets reconnection behavior (including max attempts and delay between attempts).
- This setup demonstrates how to make your node more robust, observable, and responsive to network events, which is especially useful in production or complex distributed environments.

## Parameter Details

- **LoggingOptions**: Controls the library's logging behavior.
    - `enableBaseLogging`: Enables lightweight, essential logs (recommended for most use cases).
    - `enableDebugLogging`: Enables detailed debug logs, which are much more verbose than base logs and useful for troubleshooting or development.
- **BrokerLifecycleCallbacks**: Manages the local node's connection lifecycle with the broker, especially in case of disconnection and reconnection.
    - `onInvoluntaryDisconnection`: Called when the node is disconnected from the broker unexpectedly.
    - `onReconnectionAttempt`: Called before each reconnection attempt (if reconnection is enabled).
    - `onSuccessfullyReconnected`: Called when the node successfully reconnects to the broker.
    - These callbacks are triggered only if `CrolangSettings.reconnection` is `true` and `maxReconnectionAttempts` is not empty (otherwise, reconnection is not attempted).
- **CrolangSettings**: Manages advanced connection and reconnection settings.
    - `p2pConnectionTimeoutMillis`: How many milliseconds to wait for a P2P connection attempt before timing out.
    - `multipartP2PMessageTimeoutMillis`: For large messages (see Example 10), which are split into chunks and reassembled, this defines the maximum time (in ms) allowed between receiving chunks before the multipart message is considered timed out.
    - `reconnection`: Whether the node should attempt to reconnect to the broker after an involuntary disconnection.
    - `maxReconnectionAttempts`: How many reconnection attempts to make before giving up (`Optional.empty()` means infinite attempts). If, during reconnection, the broker no longer considers the node authenticated (see Example 12), reconnection attempts will be stopped.
    - `reconnectionAttemptsDeltaMs`: How many milliseconds to wait between reconnection attempts.

## Expected Output

During execution, you will see output similar to the following (depending on network conditions and broker availability):

```
Connected to Broker at http://localhost:8080 as Alice
[DEBUG] ... (detailed debug logs enabled)
Involuntary disconnection from Broker: <cause>
Attempting to reconnect to Broker
Successfully reconnected to Broker
Failed to connect to Broker: <error>
```

The actual output will include detailed logs and lifecycle event messages, reflecting the custom configuration.

## Running the example
### 1: Start Node Alice

In the project root, run:

```sh
./gradlew runEx11Alice
```
