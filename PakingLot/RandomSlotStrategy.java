import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomSlotStrategy implements SlotStrategy {
    private final Random random = new Random();

    @Override
    public ParkingSlot findAndReserveSlot(List<ParkingLevel> levels, Gate entryGate, List<SlotType> compatibleSlotTypes) {
        List<ParkingSlot> candidates = new ArrayList<>();

        for (ParkingLevel level : levels) {
            for (SlotType slotType : compatibleSlotTypes) {
                candidates.addAll(level.getSlotsByType(slotType));
            }
        }

        Collections.shuffle(candidates, random);

        for (ParkingSlot slot : candidates) {
            if (slot.reserve()) {
                return slot;
            }
        }

        return null;
    }
}
