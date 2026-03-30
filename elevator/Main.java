public class Main {
    public static void main(String[] args) {
        Building building = new Building(
                10,
                3,
                new DualDirectionAssignmentStrategy(),
                new SCANSchedulingStrategy()
        );

        ElevatorSystem system = new ElevatorSystem(building);

        building.getFloors().get(2).getFloorPanel().pressUpButton();
        building.getFloors().get(7).getFloorPanel().pressDownButton();
        building.getFloors().get(1).getFloorPanel().pressUpButton();
        building.getFloors().get(8).getFloorPanel().pressDownButton();

        for (int i = 0; i < 8; i++) {
            System.out.println("\n--- TICK " + (i + 1) + " ---");
            system.tick();
        }

        ElevatorPanel panel1 = building.getElevatorPanels().get(0);
        panel1.pressFloorButton(9);
        panel1.pressDoorOpen();
        panel1.pressDoorClose();

        for (int i = 0; i < 10; i++) {
            System.out.println("\n--- TICK AFTER INTERNAL REQUEST " + (i + 1) + " ---");
            system.tick();
        }

        building.getController().setAssignmentStrategy(new NearestCarAssignmentStrategy());
        building.getElevators().get(0).setSchedulingStrategy(new SimpleNearestStopSchedulingStrategy());

        building.getFloors().get(5).getFloorPanel().pressDownButton();
        System.out.println("\n--- TICK AFTER STRATEGY CHANGE ---");
        system.tick();

        panel1.pressAlarm();
        system.tick();

        building.getController().resetGlobalAlarm();
        system.tick();
    }
}
