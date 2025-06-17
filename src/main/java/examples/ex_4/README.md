# Example 4: Different ways of establishing P2P connections
## Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Connection Modes](#connection-modes)
- [Callbacks](#callbacks)
- [Sub-examples](#sub-examples)
    - [4A: Single node, synchronous](#4a-single-node-synchronous)
    - [4B: Multiple nodes, synchronous](#4b-multiple-nodes-synchronous)
    - [4C: Single node, asynchronous](#4c-single-node-asynchronous)
    - [4D: Multiple nodes, asynchronous](#4d-multiple-nodes-asynchronous)
- [Running the examples](#running-the-examples)

## Learning Objectives

- Understand the difference between synchronous and asynchronous connection methods.
- Learn how to connect to a single node or multiple nodes in both modes.
- Explore all the optional callbacks available for customizing node behavior during the P2P connection lifecycle.

## Involved Files

- `Ex_4_A_Alice.java`: Alice implementation for single node, synchronous connection.
- `Ex_4_B_Alice.java`: Alice implementation for multiple nodes, synchronous connection.
- `Ex_4_C_Alice.java`: Alice implementation for single node, asynchronous connection.
- `Ex_4_D_Alice.java`: Alice implementation for multiple nodes, asynchronous connection.
- `Ex_4_Bob.java`: Bob implementation (shared across all sub-examples).
- `Ex_4_Carol.java`: Carol implementation (shared across all sub-examples).
- `Constants.java`: Common constants (IDs, broker address).

## Example Overview

All Alice implementations in this example aim to achieve the same goal: establish a P2P connection with Bob and/or Carol, exchange messages, and handle connection events using callbacks. The difference lies in how the connection is performed:
- 4A: Single node, synchronous
- 4B: Multiple nodes, synchronous
- 4C: Single node, asynchronous
- 4D: Multiple nodes, asynchronous

This allows you to compare the different approaches and choose the one that best fits your application's requirements.

Bob and Carol have a single implementation each, as their logic is valid for all four scenarios.

The examples also illustrate all the optional callbacks that can be set when establishing connections; these callbacks allow you to customize the behavior of your node for every stage of the P2P connection lifecycle.

## Connection Modes

- **Synchronous single node:** Block until connection to one node is established or fails.
- **Synchronous multiple nodes:** Block until connections to all specified nodes are established or fail.
- **Asynchronous single node:** Initiate connection to one node and handle result via callbacks.
- **Asynchronous multiple nodes:** Initiate connections to multiple nodes in parallel and handle each result via callbacks.

## Callbacks

All connection methods support a rich set of optional callbacks, including:
- `onConnectionSuccess`: Called when a connection is successfully established.
- `onConnectionFailed`: Called when a connection attempt fails.
- `onDisconnection`: Called when a node disconnects.
- `onNewMsg`: Called when a message is received from a connected node.
- `onConnectionAttempt`: (for incoming connections) Called when another node attempts to connect.

You can provide only the callbacks you need, allowing for flexible and minimal implementations or full control over the connection lifecycle.

## Sub-examples

### 4A: Single node, synchronous
Alice connects to Bob using a blocking (synchronous) call. The connection attempt completes before the program continues. All relevant callbacks (success, failure, message, disconnection) are demonstrated. This is useful when you want to ensure the connection is established before proceeding with further logic. You will see output for each callback triggered, such as successful connection, message reception, and disconnection events.

### 4B: Multiple nodes, synchronous
Alice connects to both Bob and Carol using a blocking call that waits for all connections to complete. Callbacks are set for each node, and you can observe how the logic handles multiple peers. This approach is useful for scenarios where you need to establish a group of connections before starting communication.

### 4C: Single node, asynchronous
Alice initiates a non-blocking connection to Bob. The result is handled via callbacks, allowing Alice to perform other tasks while waiting for the connection. This is ideal for applications where you want to continue other work while waiting for the connection to complete.

### 4D: Multiple nodes, asynchronous
Alice initiates non-blocking connections to both Bob and Carol in parallel. Each connection's result is handled independently via callbacks. This is useful for scalable or event-driven applications where you want to manage multiple connections concurrently.

## Running the examples
1. **Start Bob and Carol**
    - In two separate terminals, from the root of the example project, run:
      ```sh
      ./gradlew runEx4Bob
      ./gradlew runEx4Carol
      ```
2. **Start Alice for the desired sub-example**
    - In a new terminal, from the root of the example project, run one of:
      ```sh
      ./gradlew runEx4AAlice
      ./gradlew runEx4BAlice
      ./gradlew runEx4CAlice
      ./gradlew runEx4DAlice
      ```
