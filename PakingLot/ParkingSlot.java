import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParkingSlot {
    private final String slotId;
    private final SlotType slotType;
    private final Map<String, Double> distanceToGateMap;
    private boolean occupied;

    public ParkingSlot(String slotId, SlotType slotType, Map<String, Double> distanceToGateMap) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new IllegalArgumentException("Slot id cannot be empty.");
        }

        if (slotType == null) {
            throw new IllegalArgumentException("Slot type cannot be null.");
        }

        if (distanceToGateMap == null || distanceToGateMap.isEmpty()) {
            throw new IllegalArgumentException("Distance map cannot be empty.");
        }

        this.slotId = slotId.trim();
        this.slotType = slotType;
        this.distanceToGateMap = Collections.unmodifiableMap(new HashMap<>(distanceToGateMap));
        this.occupied = false;
    }

    public String getSlotId() {
        return slotId;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public double getDistanceFromGate(String gateId) {
        return distanceToGateMap.getOrDefault(gateId, Double.MAX_VALUE);
    }

    public synchronized boolean isOccupied() {
        return occupied;
    }

    public synchronized boolean reserve() {
        if (occupied) {
            return false;
        }

        occupied = true;
        return true;
    }

    public synchronized void release() {
        occupied = false;
    }

    @Override
    public String toString() {
        return "ParkingSlot{"
            + "slotId='" + slotId + '\''
            + ", slotType=" + slotType
            + ", occupied=" + occupied
            + '}';
    }
}
