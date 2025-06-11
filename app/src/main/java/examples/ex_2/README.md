# Example 2: Connecting to the CrolangP2P Broker
> **Note:** This example demonstrates the usage of the [`crolang-p2p-node-jvm`](https://github.com/crolang-p2p/crolang-p2p-node-jvm) library.

# Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Running the example](#running-the-example)
  - [Requirements](#requirements)
  - [Execution steps](#execution-steps)
    - [1.A: Starting the CrolangP2P Broker using Docker](#1a-starting-the-crolangp2p-broker-using-docker)
    - [1.B: Starting the CrolangP2P Broker using NodeJs](#1b-starting-the-crolangp2p-broker-using-nodejs)
    - [2: Start Node Alice](#2-start-node-alice)

## Learning Objectives
This example focuses on the fundamental step of connecting a node to the [CrolangP2P Broker](https://github.com/crolang-p2p/crolang-p2p-broker). It explains:

- What the Broker is: the [CrolangP2P Broker](https://github.com/crolang-p2p/crolang-p2p-broker) is a scalable socket server that forms the backbone of the Crolang project. Nodes authenticate and connect to the Broker, establishing a communication channel with it.  
When a Node intends to connect with another Node, the Broker facilitates the exchange of signaling messages between 
the two Nodes (which are not yet directly connected via P2P). This process enables both Nodes to gather the necessary  information to establish a P2P connection. Essentially, the Broker acts as a known intermediary for the initial handshake, allowing Nodes to discover each other and negotiate the parameters for a direct, peer-to-peer connection. For more details, see the documentation in the [broker repository](https://github.com/crolang-p2p/crolang-p2p-broker).
- For each node ID, only one client with that ID can be connected to the Broker at a time. If you try to connect a second client with the same ID, the Broker will reject the connection. This ensures that each node in the network has a unique identity and avoids conflicts.
- In this example, there is no authentication required to connect to the Broker. This is done for simplicity. Authentication and security topics will be covered in a future example.
- How to connect to the Broker using the CrolangP2P library, and how to handle possible connection errors (e.g., duplicate IDs, unsupported client versions, authorization failures).

## Involved Files

- [`src/main/java/examples/ex_2/Ex_2_Alice.java`](src/main/java/examples/ex_2/Ex_2_Alice.java): Node Alice logic for connecting to the Broker and handling connection errors.
- [`src/main/java/examples/Constants.java`](src/main/java/examples/Constants.java): Common constants (IDs, broker address).

## Example Overview
In this scenario, Alice attempts to connect to the [CrolangP2P Broker](https://github.com/crolang-p2p/crolang-p2p-broker) using her unique node ID. The Broker acts as a central point for node registration and discovery. The connection process includes:

- Attempting to connect to the Broker at a specified address with a given node ID.
- Handling various connection errors, such as:
  - The local client is already connected.
  - Another client with the same ID is already connected.
  - The client version is not supported by the Broker.
  - Unauthorized connection attempts.
  - Socket or configuration errors.
- Upon successful connection, Alice is registered with the Broker and can be discovered by other nodes for P2P communication.

This example demonstrates the importance of the [Broker](https://github.com/crolang-p2p/crolang-p2p-broker) in the CrolangP2P architecture and shows how to robustly handle the connection process in your node implementation. For further documentation, refer to the broker repository.

## Running the example
### Requirements
- **Java 11 or higher**: Make sure the command `java -version` returns at least version 11.
- **Crolang Broker**: You can choose one of the following options:
   - **Docker**: Have Docker installed to quickly start the broker using a container.
   - **Node.js**: Alternatively, you can clone the broker repository and run it using NodeJs. You can find the broker repository here: [crolang-p2p-broker](https://github.com/crolang-p2p/crolang-p2p-broker) (additional documentation available there).

### Execution steps
1. Run the CrolangP2P Broker, either by:  
    1.A [Using Docker](#1a-starting-the-crolangp2p-broker-using-docker)  
    1.B [Using NodeJs](#1b-starting-the-crolangp2p-broker-using-nodejs)
2. [Start Node Alice](#2-start-node-alice)

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

#### 2: Start Node Alice

In the project root, run:

```sh
./gradlew runEx2Alice
```

---
