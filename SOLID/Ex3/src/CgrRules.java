public class CgrRules implements Rules {
    @Override
    public Reason evaluate(StudentProfile s) {
        if (s.cgr < 8.0) {
            return new Reason(false, "CGR below 8.0");
        }
        return new Reason(true, "");
    }
}
