import java.nio.charset.StandardCharsets;

public class JsonExporter implements Exporter {
    @Override
    public ExportResult export(ExportRequest req) {
        try {
            ExportValidator.validate(req);
        } catch (IllegalArgumentException e) {
            return ExportResult.error(e.getMessage());
        }

        String json = "{\"title\":\"" + escape(req.title) + "\",\"body\":\"" + escape(req.body) + "\"}";
        return ExportResult.ok("application/json", json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
