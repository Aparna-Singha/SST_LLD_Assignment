public class Display {
    public void show(int elevatorId, int floor, Direction direction, ElevatorState state) {
        System.out.println("Display => Elevator " + elevatorId +
                " | Floor: " + floor +
                " | Direction: " + direction +
                " | State: " + state);
    }
}
