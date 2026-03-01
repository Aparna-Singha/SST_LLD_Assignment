package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable IncidentTicket using Builder Pattern.
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;

    private final String description;
    private final String priority;       // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;    // optional
    private final String source;         // CLI, WEBHOOK, EMAIL

    // Private constructor
    private IncidentTicket(Builder builder) {
        this.id = builder.id;
        this.reporterEmail = builder.reporterEmail;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.tags = Collections.unmodifiableList(new ArrayList<>(builder.tags));
        this.assigneeEmail = builder.assigneeEmail;
        this.customerVisible = builder.customerVisible;
        this.slaMinutes = builder.slaMinutes;
        this.source = builder.source;
    }

    // Getters only (no setters)
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; }
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }
    public static class Builder {
        private final String id;
        private final String reporterEmail;
        private final String title;
        private String description;
        private String priority = "LOW"; 
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible = false;
        private Integer slaMinutes;
        private String source;

        public Builder(String id, String reporterEmail, String title) {
            this.id = Objects.requireNonNull(id, "id required");
            this.reporterEmail = Objects.requireNonNull(reporterEmail, "reporterEmail required");
            this.title = Objects.requireNonNull(title, "title required");
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = new ArrayList<>(tags);
            return this;
        }

        public Builder assigneeEmail(String assigneeEmail) {
            this.assigneeEmail = assigneeEmail;
            return this;
        }

        public Builder customerVisible(boolean customerVisible) {
            this.customerVisible = customerVisible;
            return this;
        }

        public Builder slaMinutes(Integer slaMinutes) {
            this.slaMinutes = slaMinutes;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public IncidentTicket build() {
            // Centralized validation
            if (priority != null) {
                if (!priority.equals("LOW") &&
                    !priority.equals("MEDIUM") &&
                    !priority.equals("HIGH") &&
                    !priority.equals("CRITICAL")) {
                    throw new IllegalArgumentException("Invalid priority");
                }
            }

            return new IncidentTicket(this);
        }
    }
}