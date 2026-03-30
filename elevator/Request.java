public abstract class Request {
    protected final int sourceFloor;
    protected final long timestamp;

    protected Request(int sourceFloor) {
        this.sourceFloor = sourceFloor;
        this.timestamp = System.currentTimeMillis();
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
