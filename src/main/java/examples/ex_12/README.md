# Example 12: Authenticating to the Broker
## Table of Contents
- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [How Authentication Works](#how-authentication-works)
- [Running the Example](#running-the-example)
- [References](#references)

## Learning Objectives
- Understand how to enable and configure node authentication in CrolangP2P using a broker extension.
- Learn how to pass custom authentication data from the node to the broker and extension.
- See how to run a multi-service CrolangP2P system with authentication using Docker Compose.

## Involved Files
- Ex_12_Alice.java: Example node that connects to the broker, sending authentication data.
- docker-compose.yml: Defines and launches both the broker and authentication extension services.
- [server.js](https://github.com/crolang-p2p/examples-crolang-p2p-authentication-extension/blob/main/server.js): The authentication extension server implementation.

## Example Overview
This example shows how to:
- Launch a CrolangP2P broker and an authentication extension as separate services using Docker Compose.
- Configure the broker to delegate authentication to the extension via the `NODES_AUTHENTICATION_WEBHOOK_URL` environment variable.
- Pass custom authentication data from the node to the broker using the `onConnectionAttemptData` parameter (as a string).
- Use a sample authentication extension that expects a JSON string with `password` and `token` fields, and authenticates only if both are correct.

### How Authentication Works
- The broker is configured to call the authentication extension at `/authenticate-node` for every node connection attempt.
- The node (see `Ex_12_Alice.java`) sends a JSON string as `onConnectionAttemptData` (e.g., `{ "token": "magic-token", "password": "unicorns" }`).
- The broker forwards this data to the extension, which parses it and checks credentials.
- **In this example, the authentication extension api contained in (`server.js`) checks that:**
    - The node id is exactly `Alice`
    - The password is exactly `unicorns`
    - The token is exactly `magic-token`
- Only if all three checks pass, the node is authenticated and allowed to connect to the Broker.
- The Node's id is automatically provided by the framework, onConnectionAttempt data can contain additional data (such as token and password in this example)
- The format and logic of `onConnectionAttemptData` are fully customizable: the extension can implement any logic and data structure you need.

> **How to setup extensions:**
> For more details on how to configure and link extensions to the broker, see the [CrolangP2P Broker documentation](https://github.com/crolang-p2p/crolang-p2p-broker). This example assumes the broker is already configured to use the authentication extension via the `NODES_AUTHENTICATION_WEBHOOK_URL` environment variable in the provided `docker-compose.yml`.

## Running the Example

### Prerequisites
- Java 11 or higher
**Note:** If you are using linux on an aarch32 or aarch64 architecture, please do not use java 21, as it is not supported by the JVM implementation used by this library. Use any other version of Java 11+ instead.
- [Docker](https://www.docker.com/) installed

### Steps
1. **Start the system** (from the `ex_12` directory):
   ```sh
   docker compose up --build
   ```
   This will launch both the broker and the authentication extension, with the broker configured to use the extension for node authentication.

2. **Run the example node** (in another terminal, from the project root):
   ```sh
   ./gradlew runEx12Alice
   ```
   You should see output indicating whether authentication succeeded or failed.

3. **Stop the system:**
   Press `Ctrl+C` in the terminal running Docker Compose, then run:
   ```sh
   docker compose down
   ```

## References
- [CrolangP2P Broker repository (extensions setup)](https://github.com/crolang-p2p/crolang-p2p-broker)
- [Authentication Extension repository](https://github.com/crolang-p2p/examples-crolang-p2p-authentication-extension)
