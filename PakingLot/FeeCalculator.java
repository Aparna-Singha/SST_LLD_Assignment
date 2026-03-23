import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class FeeCalculator {
    private final Map<SlotType, Double> hourlyRateMap;

    public FeeCalculator(Map<SlotType, Double> hourlyRateMap) {
        if (hourlyRateMap == null || hourlyRateMap.isEmpty()) {
            throw new IllegalArgumentException("Hourly rate map cannot be empty.");
        }

        this.hourlyRateMap = new EnumMap<>(SlotType.class);

        for (SlotType slotType : SlotType.values()) {
            Double rate = hourlyRateMap.get(slotType);

            if (rate == null || rate < 0) {
                throw new IllegalArgumentException("Missing or invalid rate for slot type: " + slotType);
            }

            this.hourlyRateMap.put(slotType, rate);
        }
    }

    public double calculateFee(SlotType slotType, LocalDateTime entryTime, LocalDateTime exitTime) {
        if (slotType == null || entryTime == null || exitTime == null) {
            throw new IllegalArgumentException("Slot type, entry time, and exit time are required.");
        }

        if (exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }

        long minutes = Duration.between(entryTime, exitTime).toMinutes();
        long billableHours = Math.max(1L, (minutes + 59L) / 60L);

        return hourlyRateMap.get(slotType) * billableHours;
    }

    public Map<SlotType, Double> getHourlyRateMap() {
        return new EnumMap<>(hourlyRateMap);
    }
}
