import java.time.LocalDateTime;

public class SeatAvailability {
    private final String seatId;
    private final int row;
    private final int col;
    private final SeatType seatType;
    private final ShowSeatStatus status;
    private final LocalDateTime reservedUntil;

    public SeatAvailability(Seat seat, ShowSeatStatus status, LocalDateTime reservedUntil) {
        this.seatId = seat.getId();
        this.row = seat.getRow();
        this.col = seat.getCol();
        this.seatType = seat.getSeatType();
        this.status = status;
        this.reservedUntil = reservedUntil;
    }

    public String getSeatId() {
        return seatId;
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

    public ShowSeatStatus getStatus() {
        return status;
    }

    public LocalDateTime getReservedUntil() {
        return reservedUntil;
    }

    @Override
    public String toString() {
        if (status == ShowSeatStatus.RESERVED && reservedUntil != null) {
            return seatId + "(" + seatType + ") -> " + status + " until " + reservedUntil;
        }
        return seatId + "(" + seatType + ") -> " + status;
    }
}
