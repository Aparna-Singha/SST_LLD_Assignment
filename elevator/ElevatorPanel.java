import java.util.HashMap;
import java.util.Map;

public class ElevatorPanel {
    private final Elevator elevator;
    private final ElevatorController controller;
    private final Map<Integer, CabinButton> floorButtons;
    private final DoorOpenButton doorOpenButton;
    private final DoorCloseButton doorCloseButton;
    private final AlarmButton alarmButton;

    public ElevatorPanel(Elevator elevator, ElevatorController controller, int totalFloors) {
        this.elevator = elevator;
        this.controller = controller;
        this.floorButtons = new HashMap<>();
        for (int i = 0; i < totalFloors; i++) {
            floorButtons.put(i, new CabinButton(i));
        }
        this.doorOpenButton = new DoorOpenButton();
        this.doorCloseButton = new DoorCloseButton();
        this.alarmButton = new AlarmButton();
    }

    public void pressFloorButton(int destinationFloor) {
        CabinButton button = floorButtons.get(destinationFloor);
        if (button == null) {
            System.out.println("Invalid floor button: " + destinationFloor);
            return;
        }
        button.press();
        System.out.println("Elevator " + elevator.getId() + " floor button pressed: " + destinationFloor);
        elevator.addInternalRequest(new InternalRequest(elevator.getCurrentFloor(), destinationFloor, elevator.getId()));
        button.reset();
    }

    public void pressDoorOpen() {
        doorOpenButton.press();
        elevator.openDoor();
        doorOpenButton.reset();
    }

    public void pressDoorClose() {
        doorCloseButton.press();
        elevator.closeDoor();
        doorCloseButton.reset();
    }

    public void pressAlarm() {
        alarmButton.press();
        controller.triggerGlobalAlarm();
    }
}
