public class DisciplinaryRules implements Rules {
    @Override
    public Reason evaluate(StudentProfile s) {
        if (s.disciplinaryFlag != LegacyFlags.NONE) {
            return new Reason(false, "disciplinary flag present");
        }
        return new Reason(true, "");
    }
}
