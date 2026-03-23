import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NearestSlotStrategy implements SlotStrategy {
    @Override
    public ParkingSlot findAndReserveSlot(List<ParkingLevel> levels, Gate entryGate, List<SlotType> compatibleSlotTypes) {
        List<ParkingSlot> candidates = new ArrayList<>();

        for (ParkingLevel level : levels) {
            for (SlotType slotType : compatibleSlotTypes) {
                candidates.addAll(level.getSlotsByType(slotType));
            }
        }

        candidates.sort(
            Comparator
                .comparingDouble((ParkingSlot slot) -> slot.getDistanceFromGate(entryGate.getGateId()))
                .thenComparingInt(slot -> slot.getSlotType().getCapacityRank())
                .thenComparing(ParkingSlot::getSlotId)
        );

        for (ParkingSlot slot : candidates) {
            if (slot.reserve()) {
                return slot;
            }
        }

        return null;
    }
}
