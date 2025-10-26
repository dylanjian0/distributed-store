package com.distributedsystem.kvstore;

/**
 * Configuration and constants for the distributed key-value store system
 */
public class Configuration {

    // Protocol commands
    public static final String COMMAND_PUT = "PUT";
    public static final String COMMAND_GET = "GET";
    public static final String COMMAND_DELETE = "DELETE";

    // Responses
    public static final String RESPONSE_OK = "OK";
    public static final String RESPONSE_ERROR = "ERROR";
    public static final String RESPONSE_NOT_FOUND = "NOT_FOUND";

    // Default configuration
    public static final int DEFAULT_PORT = 8080;
    public static final int DEFAULT_TIMEOUT = 5000; // 5 seconds
    public static final String DEFAULT_HOST = "localhost";
}
