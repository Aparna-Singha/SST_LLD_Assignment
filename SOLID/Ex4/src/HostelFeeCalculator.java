import java.util.*;

public class HostelFeeCalculator {
    private final FakeBooking repo;
    private final Map<Integer, RoomTypes> roomPricing;
    private final Map<AddOn, MonthlyFeesCalculator> addOnPricing;

    public HostelFeeCalculator(FakeBooking repo, Map<Integer, RoomTypes> roomPricing, Map<AddOn, MonthlyFeesCalculator> addOnPricing) {
        this.repo = repo;
        this.roomPricing = roomPricing;
        this.addOnPricing = addOnPricing;
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        Money base = roomPricing.get(req.roomType).basePrice();

        Money addOnTotal = new Money(0.0);
        for (AddOn a : req.addOns) {
            addOnTotal = addOnTotal.plus(addOnPricing.get(a).fee());
        }

        return base.plus(addOnTotal);
    }
}
