public class SmartClassroomDevice {
    public IAirConditioner air;
    public IAttendanceScanner attendance;
    public ILightsPanel light;
    public IProjector projector;
    public SmartClassroomDevice(IAirConditioner air, IAttendanceScanner attendance, ILightsPanel light, IProjector projector) {
        this.air = air;
        this.attendance = attendance;
        this.light = light;
        this.projector = projector;
    }
}
