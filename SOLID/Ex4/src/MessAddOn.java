public class MessAddOn implements MonthlyFeesCalculator {
    @Override
    public Money fee() {
        return new Money(1000.0);
    }
}
