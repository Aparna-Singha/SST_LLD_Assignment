import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ElevatorController {
    private final List<Elevator> elevators;
    private final Queue<ExternalRequest> pendingRequests;
    private ElevatorAssignmentStrategy assignmentStrategy;

    public ElevatorController(List<Elevator> elevators, ElevatorAssignmentStrategy assignmentStrategy) {
        this.elevators = elevators;
        this.assignmentStrategy = assignmentStrategy;
        this.pendingRequests = new LinkedList<>();
    }

    public void setAssignmentStrategy(ElevatorAssignmentStrategy assignmentStrategy) {
        this.assignmentStrategy = assignmentStrategy;
    }

    public void submitExternalRequest(ExternalRequest request) {
        pendingRequests.offer(request);
        assignPendingRequests();
    }

    public void assignPendingRequests() {
        int size = pendingRequests.size();
        for (int i = 0; i < size; i++) {
            ExternalRequest request = pendingRequests.poll();
            Elevator elevator = assignmentStrategy.assignElevator(elevators, request);
            if (elevator == null) {
                pendingRequests.offer(request);
                continue;
            }
            System.out.println("Assigned " + request + " to Elevator " + elevator.getId());
            elevator.addExternalRequest(request);
        }
    }

    public void stepAllElevators() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
        assignPendingRequests();
    }

    public void triggerGlobalAlarm() {
        System.out.println("GLOBAL ALARM TRIGGERED");
        for (Elevator elevator : elevators) {
            elevator.emergencyStop();
        }
    }

    public void resetGlobalAlarm() {
        System.out.println("GLOBAL ALARM RESET");
        for (Elevator elevator : elevators) {
            elevator.resetEmergency();
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}
