public class CabinButton extends Button {
    private final int floorNumber;

    public CabinButton(int floorNumber) {
        super(ButtonType.CABIN_FLOOR);
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
