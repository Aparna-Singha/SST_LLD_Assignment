import java.util.function.BiPredicate;

public class PricingRule {
    private final String name;
    private final BiPredicate<Show, Seat> condition;
    private final double multiplier;

    public PricingRule(String name, BiPredicate<Show, Seat> condition, double multiplier) {
        if (multiplier < 1.0) {
            throw new IllegalArgumentException("Discount rules are not supported");
        }
        this.name = name;
        this.condition = condition;
        this.multiplier = multiplier;
    }

    public boolean applies(Show show, Seat seat) {
        return condition.test(show, seat);
    }

    public double getMultiplier() {
        return multiplier;
    }

    public double apply(double currentAmount) {
        return currentAmount * multiplier;
    }

    public String getName() {
        return name;
    }
}
