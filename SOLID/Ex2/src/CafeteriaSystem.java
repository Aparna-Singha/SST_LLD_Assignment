import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final FileStore store = new FileStore();
    private int invoiceSeq = 1000;

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    public void checkout(Customer customer, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);
        InvoiceFormatter formatter = new InvoiceFormatter(invId);

        double subtotal = Calculation.subtotal(lines, menu, formatter);
        Calculation.calculate(customer, subtotal, lines, formatter);

        String printable = formatter.generate();
        System.out.print(printable);
        store.save(invId, printable);
        
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
