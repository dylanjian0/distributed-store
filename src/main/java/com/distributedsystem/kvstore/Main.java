package com.distributedsystem.kvstore;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main entry point for the distributed key-value store
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String mode = args[0];

        switch (mode) {
            case "server":
                handleServerMode(args);
                break;
            case "client":
                handleClientMode(args);
                break;
            default:
                printUsage();
        }
    }

    private static void handleServerMode(String[] args) {
        int port = args.length > 1 ? Integer.parseInt(args[1]) : Configuration.DEFAULT_PORT;

        Node node = new Node(port);

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("\nShutting down node...");
                node.shutdown();
            } catch (IOException e) {
                System.err.println("Error shutting down: " + e.getMessage());
            }
        }));

        try {
            node.start();
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClientMode(String[] args) {
        if (args.length < 3) {
            printUsage();
            return;
        }

        String host = args[1];
        int port = Integer.parseInt(args[2]);

        Client client = new Client(host, port);

        System.out.println("Connected to server at " + host + ":" + port);
        System.out.println("Commands: PUT <key> <value>, GET <key>, DELETE <key>, QUIT");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty())
                continue;
            if (line.equalsIgnoreCase("QUIT") || line.equalsIgnoreCase("EXIT")) {
                break;
            }

            try {
                String[] parts = line.split(" ", 3);
                String command = parts[0].toUpperCase();
                String response;

                switch (command) {
                    case Configuration.COMMAND_PUT:
                        if (parts.length != 3) {
                            System.out.println("Usage: PUT <key> <value>");
                            continue;
                        }
                        response = client.put(parts[1], parts[2]);
                        System.out.println("Response: " + response);
                        break;

                    case Configuration.COMMAND_GET:
                        if (parts.length != 2) {
                            System.out.println("Usage: GET <key>");
                            continue;
                        }
                        response = client.get(parts[1]);
                        System.out.println("Value: " + response);
                        break;

                    case Configuration.COMMAND_DELETE:
                        if (parts.length != 2) {
                            System.out.println("Usage: DELETE <key>");
                            continue;
                        }
                        response = client.delete(parts[1]);
                        System.out.println("Response: " + response);
                        break;

                    default:
                        System.out.println("Unknown command: " + command);
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  Server mode: java Main server [port]");
        System.out.println("  Client mode: java Main client <host> <port>");
    }
}
