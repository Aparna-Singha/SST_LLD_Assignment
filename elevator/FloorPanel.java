public class FloorPanel {
    private final int floorNumber;
    private final int totalFloors;
    private final FloorButton upButton;
    private final FloorButton downButton;
    private final ElevatorController elevatorController;

    public FloorPanel(int floorNumber, int totalFloors, ElevatorController elevatorController) {
        this.floorNumber = floorNumber;
        this.totalFloors = totalFloors;
        this.elevatorController = elevatorController;
        this.upButton = new FloorButton(Direction.UP);
        this.downButton = new FloorButton(Direction.DOWN);
    }

    public void pressUpButton() {
        if (isTopFloor()) {
            System.out.println("Floor " + floorNumber + " is the top floor. UP request ignored.");
            return;
        }
        upButton.press();
        System.out.println("Floor " + floorNumber + " UP button pressed");
        elevatorController.submitExternalRequest(new ExternalRequest(floorNumber, Direction.UP));
        upButton.reset();
    }

    public void pressDownButton() {
        if (isGroundFloor()) {
            System.out.println("Floor " + floorNumber + " is the ground floor. DOWN request ignored.");
            return;
        }
        downButton.press();
        System.out.println("Floor " + floorNumber + " DOWN button pressed");
        elevatorController.submitExternalRequest(new ExternalRequest(floorNumber, Direction.DOWN));
        downButton.reset();
    }

    private boolean isGroundFloor() {
        return floorNumber == 0;
    }

    private boolean isTopFloor() {
        return floorNumber == totalFloors - 1;
    }
}
