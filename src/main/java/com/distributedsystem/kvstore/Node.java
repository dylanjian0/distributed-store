package com.distributedsystem.kvstore;

import java.io.IOException;

/**
 * Represents a node in the distributed key-value store cluster
 */
public class Node {

    private final int port;
    private final KeyValueStore store;
    private final ReplicationManager replicationManager;
    private final NodeServer server;

    public Node(int port) {
        this.port = port;
        this.store = new KeyValueStore();
        this.replicationManager = new ReplicationManager();
        this.server = new NodeServer(port, store, replicationManager);
    }

    public void addPeer(int peerPort) {
        if (peerPort != port) { // Don't add self
            replicationManager.addPeer(peerPort);
        }
    }

    public void start() throws IOException {
        System.out.println("Starting node on port " + port);
        server.start();
    }

    public void shutdown() throws IOException {
        server.shutdown();
    }

    public String put(String key, String value) {
        store.put(key, value);
        replicationManager.replicatePut(key, value);
        return Configuration.RESPONSE_OK;
    }

    public String get(String key) {
        String value = store.get(key);
        return value != null ? value : Configuration.RESPONSE_NOT_FOUND;
    }

    public String delete(String key) {
        store.delete(key);
        replicationManager.replicateDelete(key);
        return Configuration.RESPONSE_OK;
    }

    public KeyValueStore getStore() {
        return store;
    }

    public int getPort() {
        return port;
    }
}
