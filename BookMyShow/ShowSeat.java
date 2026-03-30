import java.time.LocalDateTime;
import java.util.Objects;

public class ShowSeat {
    private final Seat seat;
    private ShowSeatStatus status;
    private String holdId;
    private String reservedByUserId;
    private LocalDateTime reservedUntil;

    public ShowSeat(Seat seat) {
        this.seat = seat;
        this.status = ShowSeatStatus.AVAILABLE;
    }

    public Seat getSeat() {
        return seat;
    }

    public ShowSeatStatus getStatus(LocalDateTime now) {
        releaseIfExpired(now);
        return status;
    }

    public LocalDateTime getReservedUntil() {
        return reservedUntil;
    }

    public boolean isAvailable(LocalDateTime now) {
        return getStatus(now) == ShowSeatStatus.AVAILABLE;
    }

    public boolean isReservedBy(String userId, String holdId, LocalDateTime now) {
        releaseIfExpired(now);
        return status == ShowSeatStatus.RESERVED
                && Objects.equals(this.reservedByUserId, userId)
                && Objects.equals(this.holdId, holdId);
    }

    public void reserve(String userId, String holdId, LocalDateTime expiresAt, LocalDateTime now) {
        releaseIfExpired(now);
        if (status != ShowSeatStatus.AVAILABLE) {
            throw new IllegalStateException("Seat not available: " + seat.getId());
        }
        this.status = ShowSeatStatus.RESERVED;
        this.holdId = holdId;
        this.reservedByUserId = userId;
        this.reservedUntil = expiresAt;
    }

    public void confirmBooking(String userId, String holdId, LocalDateTime now) {
        if (!isReservedBy(userId, holdId, now)) {
            throw new IllegalStateException("Seat is not reserved for this booking flow: " + seat.getId());
        }
        this.status = ShowSeatStatus.BOOKED;
        clearReservationMetadata();
    }

    public void releaseReservation(String holdId) {
        if (status == ShowSeatStatus.RESERVED && Objects.equals(this.holdId, holdId)) {
            this.status = ShowSeatStatus.AVAILABLE;
            clearReservationMetadata();
        }
    }

    public void markAvailable() {
        this.status = ShowSeatStatus.AVAILABLE;
        clearReservationMetadata();
    }

    private void releaseIfExpired(LocalDateTime now) {
        if (status == ShowSeatStatus.RESERVED && reservedUntil != null && !reservedUntil.isAfter(now)) {
            markAvailable();
        }
    }

    private void clearReservationMetadata() {
        this.holdId = null;
        this.reservedByUserId = null;
        this.reservedUntil = null;
    }
}
