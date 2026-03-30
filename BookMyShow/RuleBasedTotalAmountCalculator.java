import java.util.ArrayList;
import java.util.List;

public class RuleBasedTotalAmountCalculator extends TotalAmountCalculator {
    private final List<PricingRule> pricingRules = new ArrayList<>();

    public void addRule(PricingRule rule) {
        pricingRules.add(rule);
    }

    @Override
    public double total(Show show, List<Seat> seats) {
        double totalAmount = 0.0;
        for (Seat seat : seats) {
            totalAmount += calculateSeatAmount(show, seat);
        }
        return totalAmount;
    }

    private double calculateSeatAmount(Show show, Seat seat) {
        double seatAmount = seat.getSeatType().getBasePrice();
        for (PricingRule rule : pricingRules) {
            if (rule.applies(show, seat)) {
                seatAmount = rule.apply(seatAmount);
            }
        }
        return seatAmount;
    }
}
