package com.distributedsystem.kvstore;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles data replication across nodes in the cluster
 */
public class ReplicationManager {

    private final List<Integer> peerPorts;

    public ReplicationManager() {
        this.peerPorts = new ArrayList<>();
    }

    public void addPeer(int port) {
        if (!peerPorts.contains(port)) {
            peerPorts.add(port);
        }
    }

    public void replicatePut(String key, String value) {
        replicateToAll(Configuration.COMMAND_PUT + " " + key + " " + value);
    }

    public void replicateDelete(String key) {
        replicateToAll(Configuration.COMMAND_DELETE + " " + key);
    }

    private void replicateToAll(String command) {
        for (int port : peerPorts) {
            try {
                Socket socket = new Socket("localhost", port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(command);
                socket.close();
            } catch (IOException e) {
                System.err.println("Failed to replicate to peer on port " + port + ": " + e.getMessage());
            }
        }
    }
}
