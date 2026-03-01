public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        IProjector pj = reg.getFirstOfType(IProjector.class);
        pj.powerOn();
        pj.connectInput("HDMI-1");

        ILightsPanel lights = reg.getFirstOfType(ILightsPanel.class);
        lights.setBrightness(60);

        IAirConditioner ac = reg.getFirstOfType(IAirConditioner.class);
        ac.setTemperatureC(24);

        IAttendanceScanner scan = reg.getFirstOfType(IAttendanceScanner.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }


    public void endClass() {
        System.out.println("Shutdown sequence:");
        reg.getFirstOfType(IProjector.class).powerOff();
        reg.getFirstOfType(ILightsPanel.class).powerOff();
        reg.getFirstOfType(IAirConditioner.class).powerOff();
    }
}
