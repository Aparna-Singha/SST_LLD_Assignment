import java.util.List;

public class EligibilityEngine {
    private final EligibilityEngineCalculator store;
    private final ReasonFormating printer;
    private final List<Rules> rules;

    public EligibilityEngine(EligibilityEngineCalculator store, ReasonFormating printer, List<Rules> rules) {
        this.store = store;
        this.printer = printer;
        this.rules = rules;
    }

    public void runAndPrint(StudentProfile s) {
        EligibilityEngineResult r = EligibilityEngineResult.evaluate(s, rules);
        printer.print(s, r);
        store.save(s.rollNo, r.status);
    }
}
