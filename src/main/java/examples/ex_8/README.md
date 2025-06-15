# Example 8: Sending Messages via WebSocket
## Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Expected Output](#expected-output)
- [Running the example](#running-the-example)

## Learning Objectives

This example shows how to:
- Send messages to other nodes via the WebSocket connection to the Broker (using `sendSocketMsg`).
- Handle errors that may occur when sending messages through the WebSocket (e.g., not connected to the broker, sending to self, unauthorized, etc.).
- Use WebSocket-based communication as an alternative or in combination with P2P connections: this approach allows you to use the project for pure WebSocket-based messaging, or combine it with P2P for more advanced scenarios—all within a ready-to-use framework.

## Involved Files

- Ex_8_Alice.java: Alice's logic to send messages via WebSocket and handle errors.
- Ex_8_Bob.java: Bob's logic to receive and print messages from the WebSocket.
- Constants.java: Common constants (IDs, broker address).

## Example Overview

- Alice and Bob both connect to the Broker.
- Alice sends two messages to Bob via the WebSocket connection: one on `GREETINGS_CHANNEL` and one on `SECRET_CHANNEL`.
- Bob receives and prints both messages, showing the channel and sender.
- Alice's code demonstrates error handling for possible issues when sending messages via WebSocket.

## Expected Output

During execution, you will see output similar to the following:
```
Connected to Broker at ws://localhost:8080 as Bob
Connected to Broker at ws://localhost:8080 as Alice
[GREETINGS_CHANNEL WebSocket][Alice]: Hello from Alice!
[SECRET_CHANNEL WebSocket][Alice]: 42
```

If an error occurs while Alice is sending a message (for example, if she tries to send a message to herself or is not connected to the broker), an appropriate error message will be printed by Alice.

This demonstrates that Alice can send messages to Bob directly via the WebSocket connection to the Broker, and Bob receives and prints them accordingly. Error handling ensures that issues are reported clearly.

## Running the example
### Requirements
- **Java 11 or higher**: Make sure the command `java -version` returns at least version 11.
- **Crolang Broker running**: Start the CrolangP2P Broker using one of the methods defined in the [project's general README](../../../../../README.md).

**Note:** If you are using Linux on an aarch32 or aarch64 architecture, please do not use Java 21, as it is not supported by the JVM implementation used by this library. Use any other version of Java 11+ instead.

### Execution steps
1. [Start Node Bob](#1-start-node-bob)
2. [Start Node Alice](#2-start-node-alice)

---

#### 1: Start Node Bob

In the project root, run:

```sh
./gradlew runEx8Bob
```

#### 2: Start Node Alice

In a separate terminal, run:

```sh
./gradlew runEx8Alice
```