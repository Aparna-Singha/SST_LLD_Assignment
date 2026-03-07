package com.example.reports;

/**
 * Client helper that depends on the report abstraction.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
