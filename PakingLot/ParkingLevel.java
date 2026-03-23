import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ParkingLevel {
    private final String levelId;
    private final Map<SlotType, List<ParkingSlot>> slotMapping;

    public ParkingLevel(String levelId, Map<SlotType, List<ParkingSlot>> slotMapping) {
        if (levelId == null || levelId.trim().isEmpty()) {
            throw new IllegalArgumentException("Level id cannot be empty.");
        }

        if (slotMapping == null) {
            throw new IllegalArgumentException("Slot mapping cannot be null.");
        }

        this.levelId = levelId.trim();
        this.slotMapping = new EnumMap<>(SlotType.class);

        for (SlotType slotType : SlotType.values()) {
            List<ParkingSlot> slots = slotMapping.getOrDefault(slotType, Collections.emptyList());
            this.slotMapping.put(slotType, Collections.unmodifiableList(new ArrayList<>(slots)));
        }
    }

    public String getLevelId() {
        return levelId;
    }

    public List<ParkingSlot> getSlotsByType(SlotType slotType) {
        return slotMapping.getOrDefault(slotType, Collections.emptyList());
    }

    public Map<SlotType, List<ParkingSlot>> getSlotMapping() {
        return Collections.unmodifiableMap(slotMapping);
    }
}
