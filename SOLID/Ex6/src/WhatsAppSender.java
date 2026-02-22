public class WhatsAppSender implements NotificationSender {
    private final AuditLog audit;

    public WhatsAppSender(AuditLog audit) {
        this.audit = audit;
    }

    @Override
    public SendResult send(Notification n) {
        if (n == null) {
            audit.add("wa attempted");
            return SendResult.error("notification is null");
        }
        if (n.body == null) {
            audit.add("wa attempted");
            return SendResult.error("body is missing");
        }
        if (n.phone == null || !n.phone.startsWith("+")) {
            audit.add("wa attempted");
            return SendResult.error("phone must start with + and country code");
        }

        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
        return SendResult.ok();
    }
}