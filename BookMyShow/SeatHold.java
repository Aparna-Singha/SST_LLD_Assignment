import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeatHold {
    private final String id;
    private final User user;
    private final Show show;
    private final List<Seat> seats;
    private final LocalDateTime reservedAt;
    private final LocalDateTime expiresAt;
    private SeatHoldStatus status;

    public SeatHold(String id, User user, Show show, List<Seat> seats, LocalDateTime reservedAt, LocalDateTime expiresAt) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = Collections.unmodifiableList(new ArrayList<>(seats));
        this.reservedAt = reservedAt;
        this.expiresAt = expiresAt;
        this.status = SeatHoldStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public SeatHoldStatus getStatus() {
        return status;
    }

    public boolean isActive() {
        return status == SeatHoldStatus.ACTIVE;
    }

    public boolean isExpired(LocalDateTime now) {
        return !expiresAt.isAfter(now);
    }

    public void markExpired() {
        this.status = SeatHoldStatus.EXPIRED;
    }

    public void markConfirmed() {
        this.status = SeatHoldStatus.CONFIRMED;
    }

    public void markReleased() {
        this.status = SeatHoldStatus.RELEASED;
    }
}
