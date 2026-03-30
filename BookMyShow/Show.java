import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Show {
    private final String id;
    private final Movie movie;
    private final Theatre theatre;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final Map<String, ShowSeat> showSeats = new LinkedHashMap<>();

    public Show(String id, Movie movie, Theatre theatre, Screen screen, LocalDateTime startTime) {
        this.id = id;
        this.movie = movie;
        this.theatre = theatre;
        this.screen = screen;
        this.startTime = startTime;
        initializeShowSeats();
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public ShowSeat getShowSeat(String seatId) {
        return showSeats.get(seatId);
    }

    public Collection<ShowSeat> getAllShowSeats() {
        return showSeats.values();
    }

    private void initializeShowSeats() {
        for (Seat seat : screen.getSeats()) {
            showSeats.put(seat.getId(), new ShowSeat(seat));
        }
    }

    @Override
    public String toString() {
        return "Show{id='" + id + "', movie='" + movie.getTitle() + "', theatre='" + theatre.getName() + "', screen='" + screen.getName() + "', startTime=" + startTime + "}";
    }
}
