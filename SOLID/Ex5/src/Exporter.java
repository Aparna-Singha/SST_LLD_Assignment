public interface Exporter {
    // implied "contract" but not enforced (smell)
    public ExportResult export(ExportRequest req);
}
