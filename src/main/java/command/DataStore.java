package command;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Simple in-memory data store shared by all commands
public class DataStore {
    public final Map<String, String> dataStore = new ConcurrentHashMap<>();
    public final Map<String, Long> expiryStore = new ConcurrentHashMap<>();
    public final Map<String, List<String>> listStore = new ConcurrentHashMap<>();
}
