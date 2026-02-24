public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");
        IPlagiarismChecker plagiarismChecker = new PlagiarismChecker();
        IReportWriter reportWriter = new ReportWriter();
        ICodeGrader codeGrader = new CodeGrader();
        new EvaluationPipeline(plagiarismChecker, reportWriter, codeGrader).evaluate(sub);
    }
}
