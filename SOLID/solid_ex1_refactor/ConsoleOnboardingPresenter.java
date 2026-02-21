import java.util.List;

public class ConsoleOnboardingPresenter implements OnboardingPresenter {
    @Override
    public void showInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    @Override
    public void showErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) System.out.println("- " + e);
    }

    @Override
    public void showSuccess(StudentRecord rec, int totalStudents) {
        System.out.println("OK: created student " + rec.id);
        System.out.println("Saved. Total students: " + totalStudents);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}
