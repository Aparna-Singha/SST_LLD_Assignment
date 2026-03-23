import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Gate gate1 = new Gate("G1");
        Gate gate2 = new Gate("G2");

        ParkingSlot s1 = new ParkingSlot("L1-S1", SlotType.SMALL, Map.of("G1", 1.0, "G2", 8.0));
        ParkingSlot s2 = new ParkingSlot("L1-S2", SlotType.SMALL, Map.of("G1", 4.0, "G2", 7.0));
        ParkingSlot m1 = new ParkingSlot("L1-M1", SlotType.MEDIUM, Map.of("G1", 2.0, "G2", 5.0));
        ParkingSlot l1 = new ParkingSlot("L1-L1", SlotType.LARGE, Map.of("G1", 6.0, "G2", 2.0));
        ParkingSlot m2 = new ParkingSlot("L2-M1", SlotType.MEDIUM, Map.of("G1", 5.0, "G2", 3.0));
        ParkingSlot l2 = new ParkingSlot("L2-L1", SlotType.LARGE, Map.of("G1", 7.0, "G2", 1.0));

        Map<SlotType, List<ParkingSlot>> level1Slots = new EnumMap<>(SlotType.class);
        level1Slots.put(SlotType.SMALL, Arrays.asList(s1, s2));
        level1Slots.put(SlotType.MEDIUM, Arrays.asList(m1));
        level1Slots.put(SlotType.LARGE, Arrays.asList(l1));

        Map<SlotType, List<ParkingSlot>> level2Slots = new EnumMap<>(SlotType.class);
        level2Slots.put(SlotType.MEDIUM, Arrays.asList(m2));
        level2Slots.put(SlotType.LARGE, Arrays.asList(l2));

        ParkingLevel level1 = new ParkingLevel("L1", level1Slots);
        ParkingLevel level2 = new ParkingLevel("L2", level2Slots);

        Map<SlotType, Double> hourlyRates = new EnumMap<>(SlotType.class);
        hourlyRates.put(SlotType.SMALL, 20.0);
        hourlyRates.put(SlotType.MEDIUM, 40.0);
        hourlyRates.put(SlotType.LARGE, 80.0);

        ParkingLot parkingLot = new ParkingLot(
            Arrays.asList(level1, level2),
            Arrays.asList(gate1, gate2),
            new FeeCalculator(hourlyRates)
        );

        LocalDateTime now = LocalDateTime.now();

        Ticket bikeTicket = parkingLot.park(
            new Vehicle("KA01AB1234", VehicleType.BIKE),
            now.minusHours(2).minusMinutes(10),
            SlotType.SMALL,
            "G1"
        );

        Ticket carTicket = parkingLot.park(
            new Vehicle("KA02CD5678", VehicleType.CAR),
            now.minusMinutes(75),
            SlotType.MEDIUM,
            "G2"
        );

        System.out.println("Bike ticket: " + bikeTicket);
        System.out.println("Car ticket: " + carTicket);
        System.out.println("Current status: " + parkingLot.status());

        double bikeBill = parkingLot.exit(bikeTicket, now);
        System.out.println("Bill for " + bikeTicket.getTicketId() + " = " + bikeBill);

        System.out.println("Status after exit: " + parkingLot.status());
    }
}
