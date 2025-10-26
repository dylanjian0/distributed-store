# Distributed Key-Value Store

A simple distributed key-value store implemented in Java for learning distributed systems concepts.

## Project Structure

```
.
├── pom.xml                      # Maven configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/distributedsystem/kvstore/
│   │           ├── KeyValueStore.java      # Core storage
│   │           ├── Node.java               # Node representation
│   │           ├── NodeServer.java         # Server for node connections
│   │           ├── Client.java             # Client interface
│   │           ├── ReplicationManager.java # Data replication
│   │           ├── Configuration.java      # Constants & config
│   │           └── Main.java               # Entry point
│   └── test/
│       └── java/
│           └── com/distributedsystem/kvstore/
│               └── KeyValueStoreTest.java  # Unit tests
└── README.md
```

## Running the Project

### Compile the project:

```bash
mkdir -p target/classes
javac -d target/classes -sourcepath src/main/java src/main/java/com/distributedsystem/kvstore/*.java
```

### Start a server node:

```bash
./run_server.sh [port]
# or
java -cp target/classes com.distributedsystem.kvstore.Main server 8080
```

### Use a client:

```bash
./run_client.sh [host] [port]
# or
java -cp target/classes com.distributedsystem.kvstore.Main client localhost 8080
```

### Client Commands:

Once connected, you can use:

- `PUT <key> <value>` - Store a key-value pair
- `GET <key>` - Retrieve a value by key
- `DELETE <key>` - Remove a key-value pair
- `QUIT` - Exit the client

## Implementation Summary

✅ **Complete implementation** with:

- **KeyValueStore**: Thread-safe in-memory storage using ConcurrentHashMap
- **NodeServer**: Multi-threaded TCP server handling client connections
- **Client**: Network client with clean API for PUT/GET/DELETE operations
- **ReplicationManager**: Asynchronous replication across peer nodes
- **Node**: Orchestrates store, server, and replication
- **Main**: Interactive CLI for server and client modes

## Concepts Demonstrated

- **Distributed Systems**: Multiple nodes communicating over network
- **Replication**: Data replicated across nodes (asynchronous)
- **Network Programming**: Socket-based TCP communication
- **Concurrency**: Thread-safe operations and multi-threaded servers
- **Client-Server Architecture**: Standard distributed system pattern

## Example Usage

```bash
# Terminal 1: Start server
./run_server.sh 8080

# Terminal 2: Start another server (for replication demo)
./run_server.sh 8081

# Terminal 3: Use client
./run_client.sh localhost 8080
> PUT name Dylan
Response: OK
> PUT age 25
Response: OK
> GET name
Value: Dylan
> GET age
Value: 25
> DELETE name
Response: OK
> QUIT
```

## Next Steps for Enhancement

- Add persistence (save to disk)
- Implement leader election
- Add fault tolerance and recovery
- Implement consensus protocol (Raft)
- Add load balancing
- Create a web interface
- Add monitoring and metrics

## Notes

This is a simplified implementation for educational purposes. Production systems would require:

- Better error handling and recovery
- Configuration management
- Load balancing
- Security features (authentication, encryption)
- More sophisticated consistency models
- Distributed consensus algorithms

Good luck with your distributed systems learning journey! 🚀
