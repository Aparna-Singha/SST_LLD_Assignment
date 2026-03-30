public class Payment {
    private final String id;
    private final String bookingId;
    private final PaymentMode mode;
    private final double amount;
    private PaymentStatus status;

    public Payment(String id, String bookingId, PaymentMode mode, double amount) {
        this.id = id;
        this.bookingId = bookingId;
        this.mode = mode;
        this.amount = amount;
        this.status = PaymentStatus.INITIATED;
    }

    public String getId() {
        return id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
