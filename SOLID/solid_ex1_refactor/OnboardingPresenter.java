import java.util.List;

public interface OnboardingPresenter {
    void showInput(String raw);
    void showErrors(List<String> errors);
    void showSuccess(StudentRecord rec, int totalStudents);
}
