public class ElevatorSystem {
    private final Building building;

    public ElevatorSystem(Building building) {
        this.building = building;
    }

    public void tick() {
        building.getController().stepAllElevators();
    }

    public Building getBuilding() {
        return building;
    }
}
