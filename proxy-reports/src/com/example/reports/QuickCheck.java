package com.example.reports;

public class QuickCheck {

    public static void main(String[] args) {
        User student = new User("Riya", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        ReportViewer viewer = new ReportViewer();

        System.out.println("=== QuickCheck ===");
        viewer.open(facultyReport, student);
        System.out.println();
        viewer.open(facultyReport, faculty);
        System.out.println();
        viewer.open(adminReport, admin);
        System.out.println();
        viewer.open(adminReport, admin);
    }
}
