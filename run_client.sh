#!/bin/bash
# Run a client
HOST=${1:-localhost}
PORT=${2:-8080}
java -cp target/classes com.distributedsystem.kvstore.Main client $HOST $PORT

