public class TripleRooms implements RoomTypes {
    @Override
    public Money basePrice() {
        return new Money(12000.0);
    }
}
