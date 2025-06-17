# Example 10: Sending Large Amount of Data over P2P
## Table of Contents

- [Learning Objectives](#learning-objectives)
- [Involved Files](#involved-files)
- [Example Overview](#example-overview)
- [Expected Output](#expected-output)
- [Running the Example](#running-the-example)

## Learning Objectives

This example shows how:
- CrolangP2P overcomes the message size limitations of WebRTC (the underlying technology) by allowing the transfer of arbitrarily large strings between nodes.
- Large messages are automatically split into smaller chunks and reassembled on the receiving side, making the process transparent to the user.
- You can reliably send and receive very large payloads (e.g., files or long texts) over a P2P connection.
- **Note:** Physical machine limits still apply. If you attempt to send or receive data that exceeds the available RAM, the process may fail due to out-of-memory errors. Similarly, if many nodes send large amounts of data to the same node simultaneously (without the receiver having time to process them), memory exhaustion or performance issues may occur.

## Involved Files

- Ex_10_Alice.java: Alice reads a ~100MB text file and repeats its content 10 times to simulate sending ~1GB of data to Bob over P2P.
- Ex_10_Bob.java: Bob receives the large message and prints its size.
- Constants.java: Common constants (IDs, broker address).
- large_file.txt: Example file containing ~100MB of text (due to GitHub limitations, files larger than 100MB cannot be included).

## Example Overview

- Alice reads the content of a large file (`large_file.txt`, ~100MB) from resources.
- To simulate a 1GB transfer, Alice repeats the file content 10 times, creating a string of nearly 1GB.
- Alice connects to the Broker and then to Bob via P2P.
- Alice sends the entire data as a single string message to Bob using the `LARGE_DATA_TRANSFER` channel.
- CrolangP2P automatically splits the message into chunks, sends them, and reassembles the message on Bob's side.
- Bob receives the message and prints the total number of bytes received, demonstrating that the transfer was successful and complete.

## Expected Output

During execution, you will see output similar to the following. Each line is prefixed with the node that prints it:

```
Alice: Reading large file...
Alice: File read successfully. Bytes: 99786570
Alice: Repeating file content 10 times to simulate ~1GB transfer
Alice: Total bytes to send: 997865700
Alice: Connected to Broker at http://localhost:8080 as Alice
Alice: Connected to Node Bob successfully
Alice: Sending large data to Node Bob...
Bob: Connected to Broker at http://localhost:8080 as Bob
Bob: Received 997865700 bytes of data on LARGE_DATA_TRANSFER from Node Alice
```

This demonstrates that CrolangP2P can handle the transfer of very large messages, overcoming the typical size limits of WebRTC. However, the actual limit is determined by the available memory on your machine.

## Running the example
### 1: Start Node Bob

In the project root, run:

```sh
./gradlew runEx10Bob
```

### 2: Start Node Alice

In a separate terminal, run:

```sh
./gradlew runEx10Alice
```
