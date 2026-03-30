import java.util.Objects;
import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private final int capacityKg;
    private int currentLoadKg;

    private final TreeSet<Integer> upStops;
    private final TreeSet<Integer> downStops;

    private final Door door;
    private final Display display;
    private final Alarm alarm;
    private final FloorSensor floorSensor;
    private ElevatorSchedulingStrategy schedulingStrategy;

    public Elevator(int id, int initialFloor, int capacityKg, ElevatorSchedulingStrategy schedulingStrategy) {
        this.id = id;
        this.currentFloor = initialFloor;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.capacityKg = capacityKg;
        this.currentLoadKg = 0;
        this.upStops = new TreeSet<>();
        this.downStops = new TreeSet<>();
        this.door = new Door();
        this.display = new Display();
        this.alarm = new Alarm();
        this.floorSensor = new FloorSensor(initialFloor);
        this.schedulingStrategy = schedulingStrategy;
    }

    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public Direction getDirection() { return direction; }
    public ElevatorState getState() { return state; }
    public int getCapacityKg() { return capacityKg; }
    public int getCurrentLoadKg() { return currentLoadKg; }
    public int getActiveLoad() { return upStops.size() + downStops.size(); }
    public TreeSet<Integer> getUpStops() { return upStops; }
    public TreeSet<Integer> getDownStops() { return downStops; }

    public boolean isOperational() {
        return state != ElevatorState.UNDER_MAINTENANCE && state != ElevatorState.EMERGENCY_STOP;
    }

    public boolean canServeUpRequest(int floor) {
        return (direction == Direction.UP && currentFloor <= floor) || direction == Direction.IDLE;
    }

    public boolean canServeDownRequest(int floor) {
        return (direction == Direction.DOWN && currentFloor >= floor) || direction == Direction.IDLE;
    }

    public void setSchedulingStrategy(ElevatorSchedulingStrategy schedulingStrategy) {
        this.schedulingStrategy = schedulingStrategy;
    }

    public void addExternalRequest(ExternalRequest request) {
        if (request.getSourceFloor() > currentFloor) {
            upStops.add(request.getSourceFloor());
        } else if (request.getSourceFloor() < currentFloor) {
            downStops.add(request.getSourceFloor());
        } else {
            openDoor();
        }
        refreshDirectionIfIdle();
    }

    public void addInternalRequest(InternalRequest request) {
        int destination = request.getDestinationFloor();
        if (destination > currentFloor) {
            upStops.add(destination);
        } else if (destination < currentFloor) {
            downStops.add(destination);
        } else {
            openDoor();
        }
        refreshDirectionIfIdle();
    }

    private void refreshDirectionIfIdle() {
        if (direction == Direction.IDLE) {
            Integer next = schedulingStrategy.nextStop(this);
            if (next != null) {
                if (next > currentFloor) direction = Direction.UP;
                else if (next < currentFloor) direction = Direction.DOWN;
            }
        }
    }

    public void step() {
        if (!isOperational()) {
            System.out.println("Elevator " + id + " cannot move. State = " + state);
            return;
        }

        Integer nextStop = schedulingStrategy.nextStop(this);
        if (nextStop == null) {
            idle();
            return;
        }

        if (Objects.equals(nextStop, currentFloor)) {
            arriveAtFloor(currentFloor);
            return;
        }

        if (nextStop > currentFloor) {
            direction = Direction.UP;
            state = ElevatorState.MOVING_UP;
            moveOneFloorUp();
        } else {
            direction = Direction.DOWN;
            state = ElevatorState.MOVING_DOWN;
            moveOneFloorDown();
        }

        if (currentFloor == nextStop) {
            arriveAtFloor(currentFloor);
        }

        display.show(id, currentFloor, direction, state);
    }

    private void moveOneFloorUp() {
        updateCurrentFloorFromSensor(currentFloor + 1);
        System.out.println("Elevator " + id + " moved up to floor " + currentFloor);
    }

    private void moveOneFloorDown() {
        updateCurrentFloorFromSensor(currentFloor - 1);
        System.out.println("Elevator " + id + " moved down to floor " + currentFloor);
    }

    private void arriveAtFloor(int floor) {
        upStops.remove(floor);
        downStops.remove(floor);
        state = ElevatorState.DOOR_OPEN;
        openDoor();
        closeDoor();

        if (!upStops.isEmpty()) {
            direction = Direction.UP;
            state = ElevatorState.MOVING_UP;
        } else if (!downStops.isEmpty()) {
            direction = Direction.DOWN;
            state = ElevatorState.MOVING_DOWN;
        } else {
            idle();
        }
    }

    public void openDoor() {
        if (currentLoadKg > capacityKg) {
            System.out.println("Elevator " + id + ": Cannot close doors, overload condition.");
            door.open();
            return;
        }
        door.open();
    }

    public void closeDoor() {
        if (currentLoadKg > capacityKg) {
            System.out.println("Elevator " + id + ": Over capacity. Door remains open.");
            door.open();
            return;
        }
        door.close();
    }

    public void idle() {
        direction = Direction.IDLE;
        state = ElevatorState.IDLE;
        display.show(id, currentFloor, direction, state);
    }

    public void emergencyStop() {
        state = ElevatorState.EMERGENCY_STOP;
        direction = Direction.IDLE;
        alarm.ring();
        System.out.println("Elevator " + id + " emergency stopped.");
    }

    public void resetEmergency() {
        alarm.stop();
        state = ElevatorState.IDLE;
        System.out.println("Elevator " + id + " emergency reset.");
    }

    public void setUnderMaintenance(boolean maintenance) {
        state = maintenance ? ElevatorState.UNDER_MAINTENANCE : ElevatorState.IDLE;
    }

    public void updateCurrentFloorFromSensor(int floor) {
        floorSensor.updateSensedFloor(floor);
        this.currentFloor = floorSensor.getSensedFloor();
    }

    public void setCurrentLoadKg(int currentLoadKg) {
        this.currentLoadKg = currentLoadKg;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", direction=" + direction +
                ", state=" + state +
                ", upStops=" + upStops +
                ", downStops=" + downStops +
                '}';
    }
}
