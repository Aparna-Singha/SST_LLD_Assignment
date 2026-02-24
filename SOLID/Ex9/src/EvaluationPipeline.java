public class EvaluationPipeline {
    // DIP violation: high-level module constructs concretes directly
    public IPlagiarismChecker pc;
    public IReportWriter rw;
    public ICodeGrader cd;
    public EvaluationPipeline(IPlagiarismChecker pc, IReportWriter rw, ICodeGrader cd){
        this.pc = new PlagiarismChecker();
        this.rw = new ReportWriter();
        this.cd = new CodeGrader();
    }
    public void evaluate(Submission sub) {
        Rubric rubric = new Rubric();
        // PlagiarismChecker pc = new PlagiarismChecker();
        // CodeGrader grader = new CodeGrader();
        // ReportWriter writer = new ReportWriter();

        int plag = pc.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = cd.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = rw.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
