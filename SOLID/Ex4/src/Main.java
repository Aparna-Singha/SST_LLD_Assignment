import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");

        Map<Integer, RoomTypes> roomPricing = Map.of(
            LegacyRoomTypes.SINGLE, new SingleRooms(),
            LegacyRoomTypes.DOUBLE, new DoubleRooms(),
            LegacyRoomTypes.TRIPLE, new TripleRooms(),
            LegacyRoomTypes.DELUXE, new DeluxeRooms()
        );

        Map<AddOn, MonthlyFeesCalculator> addOnPricing = Map.of(
            AddOn.MESS, new MessAddOn(),
            AddOn.LAUNDRY, new LaundryAdOn(),
            AddOn.GYM, new GymAdOn()
        );

        HostelFeeCalculator calc = new HostelFeeCalculator(new FakeBookingRepo(), roomPricing, addOnPricing);

        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        calc.process(req);
    }
}
