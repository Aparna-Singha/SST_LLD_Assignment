public class AttendanceRules implements Rules {
    @Override
    public Reason evaluate(StudentProfile s) {
        if (s.attendancePct < 75) {
            return new Reason(false, "attendance below 75");
        }
        return new Reason(true, "");
    }
}
