import java.nio.charset.StandardCharsets;

public class CsvExporter implements Exporter {
  @Override
  public ExportResult export(ExportRequest req) {
    try {
      ExportValidator.validate(req);
    } catch (IllegalArgumentException e) {
      return ExportResult.error(e.getMessage());
    }

    String csv =
      "title,body\n" +
      csvEscape(req.title) + "," + csvEscape(req.body) + "\n";

    return ExportResult.ok("text/csv", csv.getBytes(StandardCharsets.UTF_8));
  }

  private String csvEscape(String s) {
    if (s == null) return "\"\"";
    // CSV: wrap in quotes, escape " as ""
    return "\"" + s.replace("\"", "\"\"") + "\"";
  }
}