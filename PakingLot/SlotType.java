public enum SlotType {
    SMALL(1),
    MEDIUM(2),
    LARGE(3);

    private final int capacityRank;

    SlotType(int capacityRank) {
        this.capacityRank = capacityRank;
    }

    public int getCapacityRank() {
        return capacityRank;
    }

    public boolean canAccommodate(VehicleType vehicleType) {
        return capacityRank >= vehicleType.getMinimumSlotType().getCapacityRank();
    }
}
