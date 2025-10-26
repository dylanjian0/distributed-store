#!/bin/bash
# Run a server node
PORT=${1:-8080}
java -cp target/classes com.distributedsystem.kvstore.Main server $PORT

