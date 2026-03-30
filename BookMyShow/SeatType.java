public enum SeatType {
    GOLD(150),
    PLATINUM(200),
    RECLINER(300);

    private final int basePrice;

    SeatType(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
