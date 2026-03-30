import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<Seat> seats;
    private final Ticket ticket;
    private BookingStatus status;
    private double totalAmount;

    public Booking(String id, User user, Show show, List<Seat> seats, Ticket ticket) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = Collections.unmodifiableList(new ArrayList<>(seats));
        this.ticket = ticket;
        this.status = BookingStatus.CREATED;
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

    public Ticket getTicket() {
        return ticket;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
