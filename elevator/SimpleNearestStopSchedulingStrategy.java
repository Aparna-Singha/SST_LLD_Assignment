import java.util.ArrayList;
import java.util.List;

public class SimpleNearestStopSchedulingStrategy implements ElevatorSchedulingStrategy {
    @Override
    public Integer nextStop(Elevator elevator) {
        List<Integer> allStops = new ArrayList<>();
        allStops.addAll(elevator.getUpStops());
        allStops.addAll(elevator.getDownStops());

        Integer best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Integer stop : allStops) {
            int distance = Math.abs(stop - elevator.getCurrentFloor());
            if (distance < bestDistance) {
                bestDistance = distance;
                best = stop;
            }
        }
        return best;
    }
}
