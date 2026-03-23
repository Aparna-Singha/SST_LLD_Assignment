public class Gate {
    private final String gateId;

    public Gate(String gateId) {
        if (gateId == null || gateId.trim().isEmpty()) {
            throw new IllegalArgumentException("Gate id cannot be empty.");
        }

        this.gateId = gateId.trim();
    }

    public String getGateId() {
        return gateId;
    }

    @Override
    public String toString() {
        return "Gate{" + "gateId='" + gateId + '\'' + '}';
    }
}
