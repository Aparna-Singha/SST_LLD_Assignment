public class ExportResult {
    public final String contentType;
    public final byte[] bytes;

    public boolean ok = false;
    public String errorMessage = null;

    private ExportResult() {
        this.contentType = null;
        this.bytes = null;
    }

    private ExportResult(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
    }

    public static ExportResult error(String message) {
        ExportResult r = new ExportResult();
        
        r.ok = false;
        r.errorMessage = message;

        return r;
    }

    public static ExportResult ok(String contentType, byte[] bytes) {
        ExportResult r = new ExportResult(contentType, bytes);
        r.ok = true;
        return r;
    }
}
