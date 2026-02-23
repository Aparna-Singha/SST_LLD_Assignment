import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);

        List<Rules> rules = List.of(
            new DisciplinaryRules(),
            new CgrRules(),
            new AttendanceRules(),
            new CreditsRules()
        );

        EligibilityEngine engine = new EligibilityEngine(
            new FakeEligibilityStore(),
            new ReportPrinter(),
            rules
        );
        engine.runAndPrint(s);
    }
}
