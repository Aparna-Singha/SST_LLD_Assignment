import java.util.List;

public class NearestCarAssignmentStrategy implements ElevatorAssignmentStrategy {
    @Override
    public Elevator assignElevator(List<Elevator> elevators, ExternalRequest request) {
        Elevator best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (!elevator.isOperational()) {
                continue;
            }
            if (elevator.getState() == ElevatorState.EMERGENCY_STOP) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());
            if (distance < bestDistance) {
                bestDistance = distance;
                best = elevator;
            }
        }
        return best;
    }
}
