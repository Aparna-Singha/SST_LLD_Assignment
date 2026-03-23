import java.time.LocalDateTime;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot parkingSlot;
    private final Gate entryGate;
    private final LocalDateTime entryTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot parkingSlot, Gate entryGate, LocalDateTime entryTime) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket id cannot be empty.");
        }

        if (vehicle == null || parkingSlot == null || entryGate == null || entryTime == null) {
            throw new IllegalArgumentException("Ticket requires vehicle, slot, gate, and entry time.");
        }

        this.ticketId = ticketId.trim();
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.entryGate = entryGate;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public String getAllocatedSlotNumber() {
        return parkingSlot.getSlotId();
    }

    public SlotType getAllocatedSlotType() {
        return parkingSlot.getSlotType();
    }

    public Gate getEntryGate() {
        return entryGate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return "Ticket{"
            + "ticketId='" + ticketId + '\''
            + ", vehicle=" + vehicle
            + ", allocatedSlotNumber='" + getAllocatedSlotNumber() + '\''
            + ", allocatedSlotType=" + getAllocatedSlotType()
            + ", entryGate='" + entryGate.getGateId() + '\''
            + ", entryTime=" + entryTime
            + '}';
    }
}
