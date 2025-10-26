package com.distributedsystem.kvstore;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory key-value store with thread-safe operations
 */
public class KeyValueStore {

    private final ConcurrentHashMap<String, String> data;

    public KeyValueStore() {
        this.data = new ConcurrentHashMap<>();
    }

    public void put(String key, String value) {
        data.put(key, value);
    }

    public String get(String key) {
        return data.get(key);
    }

    public void delete(String key) {
        data.remove(key);
    }

    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    public int size() {
        return data.size();
    }
}
