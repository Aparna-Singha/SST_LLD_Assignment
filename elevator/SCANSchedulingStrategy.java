public class SCANSchedulingStrategy implements ElevatorSchedulingStrategy {
    @Override
    public Integer nextStop(Elevator elevator) {
        if (elevator.getDirection() == Direction.UP) {
            Integer up = elevator.getUpStops().ceiling(elevator.getCurrentFloor() + 1);
            if (up != null) return up;

            Integer down = elevator.getDownStops().isEmpty() ? null : elevator.getDownStops().last();
            return down;
        }

        if (elevator.getDirection() == Direction.DOWN) {
            Integer down = elevator.getDownStops().floor(elevator.getCurrentFloor() - 1);
            if (down != null) return down;

            Integer up = elevator.getUpStops().isEmpty() ? null : elevator.getUpStops().first();
            return up;
        }

        Integer nearestUp = elevator.getUpStops().isEmpty() ? null : elevator.getUpStops().first();
        Integer nearestDown = elevator.getDownStops().isEmpty() ? null : elevator.getDownStops().last();

        if (nearestUp == null) return nearestDown;
        if (nearestDown == null) return nearestUp;

        return Math.abs(nearestUp - elevator.getCurrentFloor()) <= Math.abs(nearestDown - elevator.getCurrentFloor())
                ? nearestUp : nearestDown;
    }
}
