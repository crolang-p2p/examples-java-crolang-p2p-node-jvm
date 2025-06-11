# Example 1: Communication among Nodes with CrolangP2P
> **Note:** This example demonstrates the usage of the [`crolang-p2p-node-jvm`](https://github.com/crolang-p2p/crolang-p2p-node-jvm) library.

# Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Expected Output](#expected-output)
- [Running the example](#running-the-example)
  - [Requirements](#requirements)
  - [Execution steps](#execution-steps)
    - [1.A: Starting the CrolangP2P Broker using Docker](#1a-starting-the-crolangp2p-broker-using-docker)
    - [1.B: Starting the CrolangP2P Broker using NodeJs](#1b-starting-the-crolangp2p-broker-using-nodejs)
    - [2: Start Node Bob](#2-start-node-bob)
    - [3: Start Node Alice](#3-start-node-alice)

## Learning Objectives
This example demonstrates the basic usage of the [`crolang-p2p-node-jvm`](https://github.com/crolang-p2p/crolang-p2p-node-jvm) library to establish communication between two Crolang Nodes. 

Its main purpose is to give you a first look at what the project is about. The concepts shown here (such as the Broker, Node communication and message exchange) will be explored in more detail in the following examples.

It covers how to send messages between nodes using the CrolangP2P framework and introduces the fundamental mechanisms for peer-to-peer communication and message exchange in a programming language agnostic way.

## Involved Files

- [`src/main/java/examples/ex_1/Ex_1_Alice.java`](src/main/java/examples/ex_1/Ex_1_Alice.java): Node Alice logic and callbacks
- [`src/main/java/examples/ex_1/Ex_1_Bob.java`](src/main/java/examples/ex_1/Ex_1_Bob.java): Node Bob logic and callbacks
- [`src/main/java/examples/Constants.java`](src/main/java/examples/Constants.java): Common constants (IDs, broker address)

## Example Overview
In this scenario, two Nodes are involved: **Alice**([Ex_1_Alice.java](src/main/java/examples/ex_1/Ex_1_Alice.java)) and **Bob**([Ex_1_Bob.java](src/main/java/examples/ex_1/Ex_1_Bob.java)).

A key component in this example is the **Broker**, which acts as a well-known entity required to establish the initial peer-to-peer (P2P) connection between nodes. The Broker helps nodes discover each other and facilitates the setup of direct communication.

First, both nodes connect to the Crolang Broker.

Then, Bob allows incoming connections, defining:
- what happens when a successful connection to another Node is performed
- what happens when a message on the "GREETINGS_CHANNEL" is received by a connected Node

Alice, on the other hand, tries to connect to Bob, also defining what happens when a new message on the "GREETINGS_CHANNEL" is received.  
Once Alice successfully connects to Bob, she sends a message to Bob on the "GREETINGS_CHANNEL".

Bob (having previously defined his callbacks) will:
- print a message when Alice successfully connects to him
- print the content of the message received on the "GREETINGS_CHANNEL" and respond on the same channel to Alice.

Finally, Alice will also print the message received on the "GREETINGS_CHANNEL", as defined in its callbacks.

## Expected Output

When you run the example, you should see output similar to the following:

**Bob's terminal:**
```
Connected to Broker at http://localhost:8080 as Bob
Incoming connections are now allowed
Connected successfully to Node Alice
Received a message on GREETINGS_CHANNEL from Node Alice: Hello from Node Alice
```

**Alice's terminal:**
```
Connected to Broker at http://localhost:8080 as Alice
Connected to Node Bob
Received a message on GREETINGS_CHANNEL from Node Bob: Hi Alice, I'm Node Bob
```

## Running the example
### Requirements
- **Java 11 or higher**: Make sure the command `java -version` returns at least version 11.
- **Crolang Broker**: You can choose one of the following options:
   - **Docker**: Have Docker installed to quickly start the broker using a container.
   - **Node.js**: Alternatively, you can clone the broker repository and run it using NodeJs. You can find the broker repository here: [crolang-p2p-broker](https://github.com/crolang-p2p/crolang-p2p-broker).

### Execution steps
1. Run the CrolangP2P Broker, either by:  
    1.A [Using Docker](#1a-starting-the-crolangp2p-broker-using-docker)  
    1.B [Using NodeJs](#1b-starting-the-crolangp2p-broker-using-nodejs)
2. [Start Node Bob](#2-start-node-bob)
3. [Start Node Alice](#3-start-node-alice)

---

#### 1.A: Starting the CrolangP2P Broker using Docker

```sh
docker run --rm --name CrolangP2PBroker -p 8080:8080 crolangp2p/broker
```

This will start the Broker on port 8080, which is the default expected by the example nodes.

---

#### 1.B: Starting the CrolangP2P Broker using NodeJs

1. Clone the broker repository:
   ```sh
   git clone https://github.com/crolang-p2p/crolang-p2p-broker.git
   cd crolang-p2p-broker
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Build the project:
   ```sh
   npm run build
   ```
4. Start the broker:
   ```sh
   npm start
   ```

The Broker will be available on port 8080 by default.

---

#### 2: Start Node Bob

In the project root, run:

```sh
./gradlew runEx1Bob
```

---

#### 3: Start Node Alice

In a separate terminal, run:

```sh
./gradlew runEx1Alice
```

---
