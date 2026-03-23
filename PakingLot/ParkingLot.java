import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private final List<ParkingLevel> levels;
    private final Map<String, Gate> gatesById;
    private final FeeCalculator feeCalculator;
    private final SlotStrategy slotStrategy;
    private final Map<String, Ticket> activeTickets;

    public ParkingLot(List<ParkingLevel> levels, List<Gate> gates, FeeCalculator feeCalculator) {
        this(levels, gates, feeCalculator, new NearestSlotStrategy());
    }

    public ParkingLot(
        List<ParkingLevel> levels,
        List<Gate> gates,
        FeeCalculator feeCalculator,
        SlotStrategy slotStrategy
    ) {
        if (levels == null || levels.isEmpty()) {
            throw new IllegalArgumentException("Parking lot must have at least one level.");
        }

        if (gates == null || gates.isEmpty()) {
            throw new IllegalArgumentException("Parking lot must have at least one gate.");
        }

        if (feeCalculator == null) {
            throw new IllegalArgumentException("Fee calculator cannot be null.");
        }

        if (slotStrategy == null) {
            throw new IllegalArgumentException("Slot strategy cannot be null.");
        }

        this.levels = Collections.unmodifiableList(new ArrayList<>(levels));
        this.gatesById = buildGateMap(gates);
        this.feeCalculator = feeCalculator;
        this.slotStrategy = slotStrategy;
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public synchronized Ticket park(
        Vehicle vehicle,
        LocalDateTime entryTime,
        SlotType requestedSlotType,
        String entryGateId
    ) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }

        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time cannot be null.");
        }

        if (requestedSlotType == null) {
            throw new IllegalArgumentException("Requested slot type cannot be null.");
        }

        Gate entryGate = gatesById.get(entryGateId);

        if (entryGate == null) {
            throw new IllegalArgumentException("Unknown gate id: " + entryGateId);
        }

        List<SlotType> compatibleSlotTypes = resolveCompatibleSlotTypes(vehicle.getVehicleType(), requestedSlotType);
        ParkingSlot slot = slotStrategy.findAndReserveSlot(levels, entryGate, compatibleSlotTypes);

        if (slot == null) {
            throw new NoSlotAvailableException("No compatible slot available for vehicle " + vehicle.getVehicleNumber());
        }

        String ticketId = "TICKET-" + System.nanoTime();
        Ticket ticket = new Ticket(ticketId, vehicle, slot, entryGate, entryTime);
        activeTickets.put(ticketId, ticket);

        return ticket;
    }

    public synchronized double exit(Ticket parkingTicket, LocalDateTime exitTime) {
        if (parkingTicket == null) {
            throw new InvalidTicketException("Parking ticket cannot be null.");
        }

        if (exitTime == null) {
            throw new IllegalArgumentException("Exit time cannot be null.");
        }

        Ticket activeTicket = activeTickets.get(parkingTicket.getTicketId());

        if (activeTicket == null) {
            throw new InvalidTicketException("Invalid or already used ticket: " + parkingTicket.getTicketId());
        }

        double billAmount = feeCalculator.calculateFee(
            activeTicket.getAllocatedSlotType(),
            activeTicket.getEntryTime(),
            exitTime
        );

        activeTickets.remove(parkingTicket.getTicketId());
        activeTicket.getParkingSlot().release();

        return billAmount;
    }

    public Map<SlotType, Integer> status() {
        Map<SlotType, Integer> freeCounts = new EnumMap<>(SlotType.class);

        for (SlotType slotType : SlotType.values()) {
            freeCounts.put(slotType, 0);
        }

        for (ParkingLevel level : levels) {
            for (SlotType slotType : SlotType.values()) {
                int availableCount = 0;

                for (ParkingSlot slot : level.getSlotsByType(slotType)) {
                    if (!slot.isOccupied()) {
                        availableCount++;
                    }
                }

                freeCounts.put(slotType, freeCounts.get(slotType) + availableCount);
            }
        }

        return Collections.unmodifiableMap(freeCounts);
    }

    public List<ParkingLevel> getLevels() {
        return levels;
    }

    public List<Gate> getGates() {
        return new ArrayList<>(gatesById.values());
    }

    private Map<String, Gate> buildGateMap(List<Gate> gates) {
        Map<String, Gate> gateMap = new LinkedHashMap<>();

        for (Gate gate : gates) {
            if (gate == null) {
                throw new IllegalArgumentException("Gate cannot be null.");
            }

            Gate previous = gateMap.put(gate.getGateId(), gate);

            if (previous != null) {
                throw new IllegalArgumentException("Duplicate gate id found: " + gate.getGateId());
            }
        }

        return Collections.unmodifiableMap(gateMap);
    }

    private List<SlotType> resolveCompatibleSlotTypes(VehicleType vehicleType, SlotType requestedSlotType) {
        if (!requestedSlotType.canAccommodate(vehicleType)) {
            throw new IllegalArgumentException(
                "Requested slot type " + requestedSlotType + " cannot accommodate vehicle type " + vehicleType
            );
        }

        List<SlotType> compatibleSlotTypes = new ArrayList<>();

        for (SlotType slotType : SlotType.values()) {
            if (slotType.getCapacityRank() >= requestedSlotType.getCapacityRank()
                && slotType.canAccommodate(vehicleType)) {
                compatibleSlotTypes.add(slotType);
            }
        }

        return compatibleSlotTypes;
    }
}
