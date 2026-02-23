public class Logger {
  private final StudentRepository db;

  public Logger(StudentRepository repo) {
    this.db = repo;
  }

  public void debugArgs(String raw) {
    System.out.println("INPUT: " + raw);
  }

  public void successLog(StudentRecord saved) {
    System.out.println("OK: created student " + saved.id);
    System.out.println("Saved. Total students: " + db.count());
    System.out.println("CONFIRMATION:");
    System.out.println(saved);
  }
}
