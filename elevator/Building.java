import java.util.ArrayList;
import java.util.List;

public class Building {
    private final List<Floor> floors;
    private final List<Elevator> elevators;
    private final List<ElevatorPanel> elevatorPanels;
    private final ElevatorController controller;

    public Building(int totalFloors,
                    int elevatorCount,
                    ElevatorAssignmentStrategy assignmentStrategy,
                    ElevatorSchedulingStrategy schedulingStrategy) {
        this.elevators = new ArrayList<>();
        for (int i = 0; i < elevatorCount; i++) {
            elevators.add(new Elevator(i + 1, 0, 700, schedulingStrategy));
        }

        this.controller = new ElevatorController(elevators, assignmentStrategy);

        this.floors = new ArrayList<>();
        for (int i = 0; i < totalFloors; i++) {
            floors.add(new Floor(i, new FloorPanel(i, totalFloors, controller)));
        }

        this.elevatorPanels = new ArrayList<>();
        for (Elevator elevator : elevators) {
            elevatorPanels.add(new ElevatorPanel(elevator, controller, totalFloors));
        }
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public List<ElevatorPanel> getElevatorPanels() {
        return elevatorPanels;
    }

    public ElevatorController getController() {
        return controller;
    }
}
