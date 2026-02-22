public class EmailSender implements NotificationSender {
  private final AuditLog audit;
  public EmailSender(AuditLog audit) { this.audit = audit; }

  @Override
  public SendResult send(Notification n) {
    if (n == null) return SendResult.error("notification is null");
    if (n.email == null) return SendResult.error("email is missing");
    if (n.body == null) return SendResult.error("body is missing");

    System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
    audit.add("email sent");
    return SendResult.ok();
  }
}