# Example 8: Sending Messages via WebSocket

> **Note:** This example demonstrates the usage of the [`crolang-p2p-node-jvm`](https://github.com/crolang-p2p/crolang-p2p-node-jvm) library.

## Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Expected Output](#expected-output)
- [Running the example](#running-the-example)
    - [Requirements](#requirements)
    - [Execution steps](#execution-steps)

## Learning Objectives

This example shows how to:
- Send messages to other nodes via the WebSocket connection to the Broker (using `sendSocketMsg`).
- Handle errors that may occur when sending messages through the WebSocket (e.g., not connected to the broker, sending to self, unauthorized, etc.).
- Use WebSocket-based communication as an alternative or in combination with P2P connections: this approach allows you to use the project for pure WebSocket-based messaging, or combine it with P2P for more advanced scenarios—all within a ready-to-use framework.

## Involved Files

- `Ex_8_Alice.java`: Alice's logic to send messages via WebSocket and handle errors.
- `Ex_8_Bob.java`: Bob's logic to receive and print messages from the WebSocket.
- `Constants.java`: Common constants (IDs, broker address).

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

- Java 11 or higher
- Crolang Broker running (see previous examples for instructions)

### Execution steps

#### 1. Start the CrolangP2P Broker

You can start the Broker using either Docker or Node.js:

**A. Using Docker:**

```sh
docker run --rm --name CrolangP2PBroker -p 8080:8080 crolangp2p/broker
```

**B. Using Node.js:**

1. Clone the broker repository:
   ```sh
   git clone https://github.com/crolang-p2p/crolang-p2p-broker.git
   cd crolang-p2p-broker
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Build:
   ```sh
   npm run build
   ```
4. Start the broker:
   ```sh
   npm start
   ```

#### 2. Start Node Bob

From the root of the example project, run:

```sh
./gradlew runEx8Bob
```

#### 3. Start Node Alice

In another terminal, run:

```sh
./gradlew runEx8Alice
```

Observe the output to see messages received by Bob via both P2P and WebSocket channels.