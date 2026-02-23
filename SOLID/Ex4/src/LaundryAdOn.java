public class LaundryAdOn implements MonthlyFeesCalculator {
    @Override
    public Money fee() {
        return new Money(500.0);
    }
}
