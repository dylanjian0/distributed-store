package com.distributedsystem.kvstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KeyValueStoreTest {

    private KeyValueStore store;

    @BeforeEach
    void setUp() {
        store = new KeyValueStore();
    }

    @Test
    void testPutAndGet() {
        store.put("key1", "value1");
        String result = store.get("key1");
        assertEquals("value1", result);
    }

    @Test
    void testGetNonExistentKey() {
        String result = store.get("nonexistent");
        assertNull(result);
    }

    @Test
    void testPutOverwrite() {
        store.put("key1", "value1");
        store.put("key1", "value2");
        assertEquals("value2", store.get("key1"));
    }

    @Test
    void testDelete() {
        store.put("key1", "value1");
        store.delete("key1");
        assertNull(store.get("key1"));
        assertFalse(store.containsKey("key1"));
    }

    @Test
    void testDeleteNonExistentKey() {
        // Should not throw exception
        store.delete("nonexistent");
    }

    @Test
    void testContainsKey() {
        assertFalse(store.containsKey("key1"));
        store.put("key1", "value1");
        assertTrue(store.containsKey("key1"));
    }

    @Test
    void testSize() {
        assertEquals(0, store.size());
        store.put("key1", "value1");
        assertEquals(1, store.size());
        store.put("key2", "value2");
        assertEquals(2, store.size());
        store.delete("key1");
        assertEquals(1, store.size());
    }

    @Test
    void testMultipleKeys() {
        store.put("key1", "value1");
        store.put("key2", "value2");
        store.put("key3", "value3");

        assertEquals("value1", store.get("key1"));
        assertEquals("value2", store.get("key2"));
        assertEquals("value3", store.get("key3"));
    }

    @Test
    void testEmptyValue() {
        store.put("key1", "");
        assertEquals("", store.get("key1"));
    }
}
