public class GymAdOn implements MonthlyFeesCalculator {
    @Override
    public Money fee() {
        return new Money(300.0);
    }
}
