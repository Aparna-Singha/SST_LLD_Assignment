import java.util.List;
import java.util.Map;

public class Calculation {
  public static double subtotal(List<OrderLine> lines, Map<String, MenuItem> menu, InvoiceFormatter formatter) {
    double subtotal = 0.0;
    for (OrderLine l : lines) {
        MenuItem item = menu.get(l.itemId);
        double lineTotal = item.price * l.qty;
        subtotal += lineTotal;
        formatter.addLine(item.name, l.qty, lineTotal);
    }

    return subtotal;
  };

  public static double calculate(Customer customer, double subtotal, List<OrderLine> lines, InvoiceFormatter formatter) {
    double taxPct = customer.getTaxRules().taxPercent();
    double discount = customer.getDiscountRules().discountAmount(subtotal, lines.size());
    double tax = subtotal * (taxPct / 100.0);
    double total = subtotal + tax - discount;

    formatter.subtotal = subtotal;
    formatter.taxPct = taxPct;
    formatter.tax = tax;
    formatter.discount = discount;
    formatter.total = total;

    return total;
  };
}
