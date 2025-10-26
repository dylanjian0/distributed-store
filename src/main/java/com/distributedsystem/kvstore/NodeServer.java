package com.distributedsystem.kvstore;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server that listens for client connections and handles requests
 */
public class NodeServer {

    private final KeyValueStore store;
    private final int port;
    private final ReplicationManager replicationManager;
    private ServerSocket serverSocket;
    private boolean running = false;

    public NodeServer(int port, KeyValueStore store, ReplicationManager replicationManager) {
        this.port = port;
        this.store = store;
        this.replicationManager = replicationManager;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Server started on port " + port);

        while (running) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String request = in.readLine();
            String response = processRequest(request);
            out.println(response);
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    private String processRequest(String request) {
        if (request == null || request.trim().isEmpty()) {
            return Configuration.RESPONSE_ERROR;
        }

        String[] parts = request.split(" ");
        if (parts.length == 0) {
            return Configuration.RESPONSE_ERROR;
        }

        String command = parts[0];

        switch (command) {
            case Configuration.COMMAND_PUT:
                if (parts.length < 3) {
                    return Configuration.RESPONSE_ERROR;
                }
                store.put(parts[1], parts[2]);
                // Note: Replication is handled by the Node itself
                return Configuration.RESPONSE_OK;

            case Configuration.COMMAND_GET:
                if (parts.length < 2) {
                    return Configuration.RESPONSE_ERROR;
                }
                String value = store.get(parts[1]);
                return value != null ? value : Configuration.RESPONSE_NOT_FOUND;

            case Configuration.COMMAND_DELETE:
                if (parts.length < 2) {
                    return Configuration.RESPONSE_ERROR;
                }
                store.delete(parts[1]);
                // Note: Replication is handled by the Node itself
                return Configuration.RESPONSE_OK;

            default:
                return Configuration.RESPONSE_ERROR;
        }
    }

    public void shutdown() throws IOException {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    public boolean isRunning() {
        return running;
    }
}
