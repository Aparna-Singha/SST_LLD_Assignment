public class CreditsRules implements Rules {
    @Override
    public Reason evaluate(StudentProfile s) {
        if (s.earnedCredits < 20) {
            return new Reason(false, "credits below 20");
        }
        return new Reason(true, "");
    }
}
