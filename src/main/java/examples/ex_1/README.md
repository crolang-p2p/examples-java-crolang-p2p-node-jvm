# Example 1: Communication among Nodes with CrolangP2P
# Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Expected Output](#expected-output)
- [Running the example](#running-the-example)

## Learning Objectives
This example demonstrates the basic usage of the [`crolang-p2p-node-jvm`](https://github.com/crolang-p2p/crolang-p2p-node-jvm) library to establish communication between two Crolang Nodes. 

Its main purpose is to give you a first look at what the project is about. The concepts shown here (such as the Broker, Node communication and message exchange) will be explored in more detail in the following examples.

It covers how to send messages between nodes using the CrolangP2P framework and introduces the fundamental mechanisms for peer-to-peer communication and message exchange in a programming language agnostic way.

## Involved Files

- Ex_1_Alice.java: Node Alice logic and callbacks
- Ex_1_Bob.java: Node Bob logic and callbacks
- Constants.java: Common constants (IDs, broker address)

## Example Overview
In this scenario, two Nodes are involved: **Alice**(Ex_1_Alice.java) and **Bob**(Ex_1_Bob.java).

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
- **Crolang Broker running**: Start the CrolangP2P Broker using one of the methods defined in the [project's general README](../../../../../README.md). 

### Execution steps
1. [Start Node Bob](#1-start-node-bob)
2. [Start Node Alice](#2-start-node-alice)

---

#### 1: Start Node Bob

In the project root, run:

```sh
./gradlew runEx1Bob
```

#### 2: Start Node Alice

In a separate terminal, run:

```sh
./gradlew runEx1Alice
```
