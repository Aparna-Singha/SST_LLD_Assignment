public class InternalRequest extends Request {
    private final int destinationFloor;
    private final int elevatorId;

    public InternalRequest(int sourceFloor, int destinationFloor, int elevatorId) {
        super(sourceFloor);
        this.destinationFloor = destinationFloor;
        this.elevatorId = elevatorId;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    @Override
    public String toString() {
        return "InternalRequest{sourceFloor=" + sourceFloor + ", destinationFloor=" + destinationFloor +
                ", elevatorId=" + elevatorId + '}';
    }
}
