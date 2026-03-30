public class FloorSensor {
    private int sensedFloor;

    public FloorSensor(int initialFloor) {
        this.sensedFloor = initialFloor;
    }

    public int getSensedFloor() {
        return sensedFloor;
    }

    public void updateSensedFloor(int floor) {
        this.sensedFloor = floor;
    }
}
