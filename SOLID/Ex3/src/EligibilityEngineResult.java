import java.util.ArrayList;
import java.util.List;

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
    public static EligibilityEngineResult evaluate(StudentProfile s, List<Rules> rules) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for (Rules rule : rules) {
            Reason r = rule.evaluate(s);
            if (!r.passed) {
                status = "NOT_ELIGIBLE";
                reasons.add(r.message);
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}
