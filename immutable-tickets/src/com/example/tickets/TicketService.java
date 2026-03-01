package com.example.tickets;

/**
 * Service layer that creates tickets.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - creates partially valid objects
 * - mutates after creation (bad for auditability)
 * - validation is scattered & incomplete
 *
 * TODO (student):
 * - After introducing immutable IncidentTicket + Builder, refactor this to stop
 * mutating.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        // scattered validation (incomplete on purpose)
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("id required");
        if (reporterEmail == null || !reporterEmail.contains("@"))
            throw new IllegalArgumentException("email invalid");
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("title required");

        IncidentTicket t = new IncidentTicket.Builder(id, reporterEmail, title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .addTag("NEW")
                .build();

        return t;
    }
    public IncidentTicket escalateToCritical(IncidentTicket t) {

            return new IncidentTicket.Builder(
                    t.getId(),
                    t.getReporterEmail(),
                    t.getTitle()
            )
                    .description(t.getDescription())
                    .priority("CRITICAL")
                    .tags(t.getTags())
                    .assigneeEmail(t.getAssigneeEmail())
                    .customerVisible(t.isCustomerVisible())
                    .slaMinutes(t.getSlaMinutes())
                    .source(t.getSource())
                    .addTag("ESCALATED")
                    .build();
    }

    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {

        if (assigneeEmail != null && !assigneeEmail.contains("@")) {
            throw new IllegalArgumentException("assigneeEmail invalid");
        }

        return new IncidentTicket.Builder(
                t.getId(),
                t.getReporterEmail(),
                t.getTitle()
        )
                .description(t.getDescription())
                .priority(t.getPriority())
                .tags(t.getTags())
                .assigneeEmail(assigneeEmail)
                .customerVisible(t.isCustomerVisible())
                .slaMinutes(t.getSlaMinutes())
                .source(t.getSource())
                .build();
    }
}
