public class Alarm {
    private boolean active;

    public void ring() {
        active = true;
        System.out.println("ALARM RINGING !!!");
    }

    public void stop() {
        active = false;
        System.out.println("Alarm stopped");
    }

    public boolean isActive() {
        return active;
    }
}
