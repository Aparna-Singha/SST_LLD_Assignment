public class ExportValidator {
  public static void validate(ExportRequest req) {
    if (req == null) {
      throw new IllegalArgumentException("ExportRequest is null");
    }

    if (req.title == null) {
      throw new IllegalArgumentException("title is null");
    }

    if (req.body == null) {
      throw new IllegalArgumentException("body is null");
    }
  }
}
