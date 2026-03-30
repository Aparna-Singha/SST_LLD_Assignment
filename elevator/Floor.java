public class Floor {
    private final int floorNumber;
    private final FloorPanel floorPanel;

    public Floor(int floorNumber, FloorPanel floorPanel) {
        this.floorNumber = floorNumber;
        this.floorPanel = floorPanel;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public FloorPanel getFloorPanel() {
        return floorPanel;
    }
}
