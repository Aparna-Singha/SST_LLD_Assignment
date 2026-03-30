public class UpiRefundAmountCalculator extends RefundAmountCalculator {
    @Override
    public double refund(Booking booking) {
        return booking.getTotalAmount();
    }
}
