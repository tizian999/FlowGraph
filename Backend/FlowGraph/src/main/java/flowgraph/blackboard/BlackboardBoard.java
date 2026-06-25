package flowgraph.blackboard;

import java.util.HashMap;
import java.util.Map;

public class BlackboardBoard {

    private final Map<String, BoardEntry> entries = new HashMap<>();

    public void putDone(String key, Object value) {
        entries.put(key, new BoardEntry("DONE", value));
    }

    public void putFailed(String key, Object value) {
        entries.put(key, new BoardEntry("FAILED", value));
    }

    public Object getValue(String key) {
        BoardEntry entry = entries.get(key);
        return entry == null ? null : entry.getValue();
    }

    public Map<String, BoardEntry> getEntries() {
        return entries;
    }
}