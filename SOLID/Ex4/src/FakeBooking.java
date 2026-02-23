public interface FakeBooking {
    void save(String id, BookingRequest req, Money monthly, Money deposit);
}
