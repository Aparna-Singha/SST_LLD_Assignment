public class SmsSender implements NotificationSender {
  private final AuditLog audit;
  public SmsSender(AuditLog audit) { this.audit = audit; }

  @Override
  public SendResult send(Notification n) {
    if (n == null) return SendResult.error("notification is null");
    if (n.phone == null) return SendResult.error("phone is missing");
    if (n.body == null) return SendResult.error("body is missing");

    System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
    audit.add("sms sent");
    return SendResult.ok();
  }
}