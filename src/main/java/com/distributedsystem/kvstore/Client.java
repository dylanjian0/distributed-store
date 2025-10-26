package com.distributedsystem.kvstore;

import java.io.*;
import java.net.Socket;

/**
 * Client to interact with the distributed key-value store
 */
public class Client {

    private final String serverHost;
    private final int serverPort;

    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public String put(String key, String value) throws IOException {
        return sendCommand(Configuration.COMMAND_PUT + " " + key + " " + value);
    }

    public String get(String key) throws IOException {
        return sendCommand(Configuration.COMMAND_GET + " " + key);
    }

    public String delete(String key) throws IOException {
        return sendCommand(Configuration.COMMAND_DELETE + " " + key);
    }

    private String sendCommand(String command) throws IOException {
        try (Socket socket = new Socket(serverHost, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(command);
            return in.readLine();
        }
    }
}
