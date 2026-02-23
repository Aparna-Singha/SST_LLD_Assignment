public class InvoiceFormatter {
    public final StringBuilder out;

    public double subtotal;
    public double taxPct;
    public double tax;
    public double discount;
    public double total;

    public InvoiceFormatter(String invId) {
        out = new StringBuilder();
        out.append("Invoice# ").append(invId).append("\n");
    }

    public void addLine(String itemName, int qty, double lineTotal) {
        out.append(String.format("- %s x%d = %.2f\n", itemName, qty, lineTotal));
    }

    public String generate() {
        out.append(String.format("Subtotal: %.2f\n", subtotal));
        out.append(String.format("Tax(%.0f%%): %.2f\n", taxPct, tax));
        out.append(String.format("Discount: -%.2f\n", discount));
        out.append(String.format("TOTAL: %.2f\n", total));

        return out.toString();
    }
}
