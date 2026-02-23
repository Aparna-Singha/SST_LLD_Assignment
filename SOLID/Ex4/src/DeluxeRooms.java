public class DeluxeRooms implements RoomTypes {
    @Override
    public Money basePrice() {
        return new Money(16000.0);
    }
}
