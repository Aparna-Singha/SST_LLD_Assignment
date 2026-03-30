public class Seat {
    private final String id;
    private final int row;
    private final int col;
    private final SeatType seatType;

    public Seat(String id, int row, int col, SeatType seatType) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.seatType = seatType;
    }

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    @Override
    public String toString() {
        return id + "(" + seatType + ")";
    }
}
