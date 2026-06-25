package flowgraph.model;

public class BlackboardResponse {

    private boolean success;
    private String capability;
    private Object payload;
    private String message;

    public BlackboardResponse(boolean success, String capability, Object payload, String message) {
        this.success = success;
        this.capability = capability;
        this.payload = payload;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCapability() {
        return capability;
    }

    public Object getPayload() {
        return payload;
    }

    public String getMessage() {
        return message;
    }
}