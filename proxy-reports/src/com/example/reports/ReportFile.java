package com.example.reports;

/**
 * Legacy direct loader kept only for comparison with the proxy-based design.
 * New client code should use {@link ReportProxy} instead so access control and
 * lazy loading stay in one place.
 */
public class ReportFile implements Report {

    private final String reportId;
    private final String title;
    private final String classification; // PUBLIC / FACULTY / ADMIN
    private String content;

    public ReportFile(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        ensureLoaded();
        System.out.println("REPORT -> id=" + reportId
                + " title=" + title
                + " classification=" + classification
                + " openedBy=" + user.getName());
        System.out.println("CONTENT: " + content);
    }

    private void ensureLoaded() {
        if (content != null) {
            return;
        }

        System.out.println("[disk] loading report " + reportId + " ...");
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        content = "Internal report body for " + title;
    }
}
