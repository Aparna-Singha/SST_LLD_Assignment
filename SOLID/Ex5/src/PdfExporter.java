import java.nio.charset.StandardCharsets;

public class PdfExporter implements Exporter {
    @Override
    public ExportResult export(ExportRequest req) {
        try {
            ExportValidator.validate(req);
        } catch (IllegalArgumentException e) {
            return ExportResult.error(e.getMessage());
        }

        if (req.body.length() > 20) {
            return ExportResult.error("cannot handle content > 20 chars");
        }
        
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return ExportResult.ok("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
