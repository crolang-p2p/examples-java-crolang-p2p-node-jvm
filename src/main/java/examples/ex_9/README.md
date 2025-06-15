# Example 9: Nodes Allowing Incoming Connections and Initiating New Connections Simultaneously
## Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Expected Output](#expected-output)
- [Running the Example](#running-the-example)

## Learning Objectives

This example demonstrates that:
- Any node can both accept incoming P2P connections and initiate new outgoing P2P connections: all nodes are peers and can play both roles at the same time.
- This flexibility allows for more dynamic and robust network topologies, where nodes can connect to each other as needed, regardless of who initiated the connection.

## Involved Files

- Ex_9_Alice.java: Alice connects to Bob and requests a connection to Carol.
- Ex_9_Bob.java: Bob allows incoming connections and, upon request, initiates a new connection to Carol.
- Ex_9_Carol.java: Carol allows incoming connections and responds to messages.
- Constants.java: Common constants (IDs, broker address).

## Example Overview

- All nodes (Alice, Bob, Carol) connect to the Broker.
- Alice acts only as initiator, Carol acts only as receiver while Bob acts both as initiator and as receiver at the same time.
- For demonstration, Alice connects to Bob and asks him to connect to Carol, but the key point is that any node could do both actions.
- This pattern shows that all nodes are equal peers in the network, capable of building flexible and resilient P2P topologies.

## Expected Output

During execution, you will see output similar to the following. Each line is prefixed with the node that prints it:

```
Bob: Connected to Broker at http://localhost:8080 as Bob
Alice: Connected to Broker at http://localhost:8080 as Alice
Carol: Connected to Broker at http://localhost:8080 as Carol
Bob: Connected successfully to Node Alice
Bob: [CONNECT_TO_CAROL][Alice]
Bob: Connecting to Node Carol
Bob: Connected successfully to Node Carol
Carol: Connected successfully to Node Bob
Carol: Sending message to Node Bob: Hello Alice, I'm Node Carol
Bob: [REDIRECT_TO_ALICE][Carol]: Hello Alice, I'm Node Carol
Bob: Redirecting to Node Alice: Hello Alice, I'm Node Carol, this message was redirected by Node Bob
Alice: [REDIRECT_TO_ALICE][Bob]: Hello Alice, I'm Node Carol, this message was redirected by Node Bob
```

This demonstrates that all nodes can act as both initiators and receivers of P2P connections, highlighting the peer-to-peer nature of the framework.

## Running the example
### Requirements
- **Java 11 or higher**: Make sure the command `java -version` returns at least version 11.
- **Crolang Broker running**: Start the CrolangP2P Broker using one of the methods defined in the [project's general README](../../../../../README.md).

**Note:** If you are using Linux on an aarch32 or aarch64 architecture, please do not use Java 21, as it is not supported by the JVM implementation used by this library. Use any other version of Java 11+ instead.

### Execution steps

1. **Start Bob and Carol**
    - In two separate terminals, from the root of the example project, run:
      ```sh
      ./gradlew runEx9Bob
      ./gradlew runEx9Carol
      ```
2. **In a separate terminal, run**:
   ```sh
   ./gradlew runEx9Alice
   ```
