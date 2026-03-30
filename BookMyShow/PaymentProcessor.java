public class PaymentProcessor {
    public boolean pay(Payment payment) {
        if (payment.getAmount() <= 0) {
            payment.setStatus(PaymentStatus.FAILED);
            return false;
        }
        payment.setStatus(PaymentStatus.SUCCESS);
        return true;
    }

    public void refund(Payment payment) {
        switch (payment.getMode()) {
            case UPI:
                refundToUpi(payment);
                break;
            case CARD:
                refundToCard(payment);
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment mode: " + payment.getMode());
        }
    }

    private void refundToUpi(Payment payment) {
        payment.setStatus(PaymentStatus.REFUNDED);
    }

    private void refundToCard(Payment payment) {
        payment.setStatus(PaymentStatus.REFUNDED);
    }
}
