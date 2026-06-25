package flowgraph.blackboard;

public class BoardEntry {

    private String status;
    private Object value;

    public BoardEntry(String status, Object value) {
        this.status = status;
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public Object getValue() {
        return value;
    }
}