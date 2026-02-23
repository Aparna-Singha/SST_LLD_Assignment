public class DoubleRooms implements RoomTypes {
    @Override
    public Money basePrice() {
        return new Money(15000.0);
    }
}
