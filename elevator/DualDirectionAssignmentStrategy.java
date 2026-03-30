import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DualDirectionAssignmentStrategy implements ElevatorAssignmentStrategy {
    @Override
    public Elevator assignElevator(List<Elevator> elevators, ExternalRequest request) {
        List<Elevator> preferred = new ArrayList<>();

        for (Elevator elevator : elevators) {
            if (!elevator.isOperational()) continue;

            if (request.getDirection() == Direction.UP && elevator.canServeUpRequest(request.getSourceFloor())) {
                preferred.add(elevator);
            } else if (request.getDirection() == Direction.DOWN && elevator.canServeDownRequest(request.getSourceFloor())) {
                preferred.add(elevator);
            }
        }

        if (!preferred.isEmpty()) {
            return preferred.stream()
                    .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - request.getSourceFloor())))
                    .orElse(null);
        }

        return elevators.stream()
                .filter(Elevator::isOperational)
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - request.getSourceFloor())))
                .orElse(null);
    }
}
