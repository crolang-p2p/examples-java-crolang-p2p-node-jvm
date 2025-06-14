# Example 3: Enabling/disabling incoming P2P connections
# Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Expected Output](#expected-output)
- [Running the example](#running-the-example)

## Learning Objectives
This example focuses on two main concepts:

1. **How a Node Enables and Disables Incoming Connections:**
   - A node (like Bob) can allow incoming connections using `allowIncomingConnections`.
   - Incoming connections can also be disabled at runtime using `stopIncomingConnections`, making the node unreachable for new connection attempts.
   - When enabling incoming connections, the node can define a set of callbacks to handle connection attempts, successful connections, disconnections, and incoming messages.
   - All these callbacks are optional: you can provide only those needed for your use case, depending on how much you want to customize the node's behavior and logic.
   - If a node does not enable incoming connections, or disables them later, other nodes will not be able to connect to it, even if they know its ID.

2. **The Role and Importance of Callbacks and Disconnection Handling:**
   - The callbacks defined when enabling incoming connections allow you to control access (e.g., authorizing which nodes can connect), handle connection lifecycle events (including disconnections), and process messages on specific channels.
   - You can omit any callback you don't need; the node will use default behavior for those events.
   - Properly handling disconnections and the ability to disable incoming connections is important for robust P2P applications: you can react to remote node disconnects, clean up resources, or trigger reconnection logic as needed.
   - This flexibility lets you implement only the logic you care about, while still supporting secure and controlled peer-to-peer communication.

This example demonstrates how these mechanisms work in practice and why they are useful for customizing node behavior, handling connection/disconnection events, and dynamically controlling node accessibility in a CrolangP2P network.

## Involved Files

- Ex_3_Alice.java: Node Alice logic and callbacks for Example 3
- Ex_3_Bob.java: Node Bob logic and callbacks for Example 3
- Constants.java: Common constants (IDs, broker address)

## Example Overview
In this scenario, Bob enables incoming connections by calling `allowIncomingConnections` and provides a set of callbacks:

- `onConnectionAttempt`: Authorizes only Alice to connect.
- `onConnectionSuccess`: Prints a message when a node connects.
- `onConnectionFailed`: Prints a message if a connection attempt fails.
- `onDisconnection`: Prints a message when a node disconnects.
- `onNewMsg`: Handles messages on two channels:
  - `CHANNEL_NUMBERS`: Prints the received number from Alice.
  - `CHANNEL_DISCONNECT`: When received, Bob disconnects from Alice.

The flow is as follows:
1. Both Alice and Bob connect to the Broker.
2. Bob enables incoming connections and waits for Alice.
3. Alice connects to Bob, sends a message on `CHANNEL_NUMBERS`, then sends a message on `CHANNEL_DISCONNECT`.
4. Upon receiving `CHANNEL_DISCONNECT`, Bob disconnects from Alice.
5. Alice detects the disconnection and tries to reconnect, but fails because Bob no longer accepts connections.

This demonstrates how connection control and callbacks work, and what happens when a node stops accepting new connections.

## Expected Output

**Bob's terminal:**
```
Connected to Broker at http://localhost:8080 as Bob
Connected successfully to Node Alice
Received on CHANNEL_NUMBERS from Alice: 42
Received CHANNEL_DISCONNECT from Alice. Disconnecting...
Disconnected from node Alice
```

**Alice's terminal:**
```
Connected to Broker at http://localhost:8080 as Alice
Connected successfully to Node Bob
Disconnected from Node Bob , trying to reconnect...
Error connecting to Node Bob: Connections not allowed on remote Node
```

## Running the example
### Requirements
- **Java 11 or higher**: Make sure the command `java -version` returns at least version 11.
- **Crolang Broker running**: Start the CrolangP2P Broker using one of the methods defined in the [project's general README](../../../../../README.md).

### Execution steps
1. [Start Node Bob](#1-start-node-bob)
2. [Start Node Alice](#2-start-node-alice)

---

#### 1: Start Node Bob

In the project root, run:

```sh
./gradlew runEx3Bob
```

#### 2: Start Node Alice

In a separate terminal, run:

```sh
./gradlew runEx3Alice
```
