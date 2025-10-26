#!/bin/bash
# Simple integration test for the distributed key-value store

echo "=== Distributed Key-Value Store Integration Test ==="
echo ""

# Start server in background
echo "Starting server on port 8080..."
java -cp target/classes com.distributedsystem.kvstore.Main server 8080 &
SERVER_PID=$!
sleep 2

# Test PUT
echo ""
echo "1. Testing PUT operation..."
echo "PUT name Dylan" | java -cp target/classes com.distributedsystem.kvstore.Main client localhost 8080 > /tmp/test_output.txt 2>&1 &
sleep 1

# Test GET
echo "2. Testing GET operation..."
echo "GET name
QUIT" | java -cp target/classes com.distributedsystem.kvstore.Main client localhost 8080 > /tmp/test_output2.txt 2>&1 &
sleep 1

# Clean up
echo ""
echo "Shutting down server..."
kill $SERVER_PID 2>/dev/null

echo ""
echo "=== Test Complete ==="

