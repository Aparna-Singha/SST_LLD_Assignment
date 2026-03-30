public class CardRefundAmountCalculator extends RefundAmountCalculator {
    @Override
    public double refund(Booking booking) {
        return booking.getTotalAmount();
    }
}
