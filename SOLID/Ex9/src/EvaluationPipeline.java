public class EvaluationPipeline {
    private final IPlagiarismChecker pc;
    private final IReportWriter rw;
    private final ICodeGrader cd;

    public EvaluationPipeline(IPlagiarismChecker pc, IReportWriter rw, ICodeGrader cd){
        this.pc = pc;
        this.rw = rw;
        this.cd = cd;
    }

    public void evaluate(Submission sub) {
        Rubric rubric = new Rubric();

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
