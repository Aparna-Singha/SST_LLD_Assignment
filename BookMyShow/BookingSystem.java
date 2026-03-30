import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BookingSystem {
    private static final Duration DEFAULT_SEAT_HOLD_DURATION = Duration.ofMinutes(5);

    private final Map<String, Movie> movies = new HashMap<>();
    private final Map<String, Theatre> theatres = new HashMap<>();
    private final Map<String, Show> shows = new ConcurrentHashMap<>();
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();
    private final Map<String, Payment> paymentsByBookingId = new ConcurrentHashMap<>();
    private final Map<String, SeatHold> activeSeatHolds = new ConcurrentHashMap<>();

    private final RuleBasedTotalAmountCalculator amountCalculator;
    private final PaymentProcessor paymentProcessor;
    private final Duration seatHoldDuration;

    public BookingSystem(RuleBasedTotalAmountCalculator amountCalculator, PaymentProcessor paymentProcessor) {
        this(amountCalculator, paymentProcessor, DEFAULT_SEAT_HOLD_DURATION);
    }

    public BookingSystem(RuleBasedTotalAmountCalculator amountCalculator, PaymentProcessor paymentProcessor, Duration seatHoldDuration) {
        if (seatHoldDuration == null || seatHoldDuration.isNegative() || seatHoldDuration.isZero()) {
            throw new IllegalArgumentException("Seat hold duration must be positive");
        }
        this.amountCalculator = amountCalculator;
        this.paymentProcessor = paymentProcessor;
        this.seatHoldDuration = seatHoldDuration;
    }

    public Movie createMovie(User user, String title, int durationInMinutes, String language) {
        validateAdmin(user);
        Movie movie = new Movie(IdGenerator.newId("MOV"), title, durationInMinutes, language);
        movies.put(movie.getId(), movie);
        return movie;
    }

    public Movie addMovie(User user, String title, int durationInMinutes, String language) {
        return createMovie(user, title, durationInMinutes, language);
    }

    public Theatre createTheatre(User user, String name, String city) {
        validateAdmin(user);
        Theatre theatre = new Theatre(IdGenerator.newId("THR"), name, city);
        theatres.put(theatre.getId(), theatre);
        return theatre;
    }

    public Theatre addTheatre(User user, String name, String city) {
        return createTheatre(user, name, city);
    }

    public Screen createScreen(User user, Theatre theatre, String screenName) {
        validateAdmin(user);
        Screen screen = new Screen(IdGenerator.newId("SCR"), screenName);
        theatre.addScreen(screen);
        return screen;
    }

    public void addSeatToScreen(User user, Screen screen, Seat seat) {
        validateAdmin(user);
        screen.addSeat(seat);
    }

    public Show createShow(User user, String movieId, String theatreId, String screenId, LocalDateTime startTime) {
        validateAdmin(user);
        Movie movie = requireMovie(movieId);
        Theatre theatre = requireTheatre(theatreId);
        Screen screen = requireScreen(theatre, screenId);

        Show show = new Show(IdGenerator.newId("SHW"), movie, theatre, screen, startTime);
        shows.put(show.getId(), show);
        return show;
    }

    public Show addMovieShow(User user, String movieId, String theatreId, String screenId, LocalDateTime startTime) {
        return createShow(user, movieId, theatreId, screenId, startTime);
    }

    public List<Theatre> showTheatres(String city) {
        cleanupExpiredSeatHolds();
        return theatres.values().stream()
                .filter(theatre -> theatre.getCity().equalsIgnoreCase(city))
                .sorted(Comparator.comparing(Theatre::getName))
                .collect(Collectors.toList());
    }

    public List<Theatre> showTheatre(String city) {
        return showTheatres(city);
    }

    public List<Movie> showMovies(String city) {
        cleanupExpiredSeatHolds();
        Set<Movie> movieSet = new LinkedHashSet<>();
        for (Show show : shows.values()) {
            if (show.getTheatre().getCity().equalsIgnoreCase(city)) {
                movieSet.add(show.getMovie());
            }
        }
        return new ArrayList<>(movieSet);
    }

    public List<Movie> showMovie(String city) {
        return showMovies(city);
    }

    public List<Show> listShows(String city, String movieId) {
        cleanupExpiredSeatHolds();
        return shows.values().stream()
                .filter(show -> show.getTheatre().getCity().equalsIgnoreCase(city))
                .filter(show -> show.getMovie().getId().equals(movieId))
                .sorted(Comparator.comparing(Show::getStartTime))
                .collect(Collectors.toList());
    }

    public List<Show> showShowsForMovie(String city, String movieId) {
        return listShows(city, movieId);
    }

    public List<Movie> showMoviesInTheatre(String theatreId) {
        cleanupExpiredSeatHolds();
        requireTheatre(theatreId);
        Set<Movie> movieSet = new LinkedHashSet<>();
        for (Show show : shows.values()) {
            if (show.getTheatre().getId().equals(theatreId)) {
                movieSet.add(show.getMovie());
            }
        }
        return new ArrayList<>(movieSet);
    }

    public List<Show> showShowsInTheatre(String theatreId, String movieId) {
        cleanupExpiredSeatHolds();
        requireTheatre(theatreId);
        return shows.values().stream()
                .filter(show -> show.getTheatre().getId().equals(theatreId))
                .filter(show -> movieId == null || show.getMovie().getId().equals(movieId))
                .sorted(Comparator.comparing(Show::getStartTime))
                .collect(Collectors.toList());
    }

    public List<SeatAvailability> showSeatMap(String showId) {
        cleanupExpiredSeatHolds();
        Show show = requireShow(showId);
        LocalDateTime now = LocalDateTime.now();
        return show.getAllShowSeats().stream()
                .map(showSeat -> new SeatAvailability(showSeat.getSeat(), showSeat.getStatus(now), showSeat.getReservedUntil()))
                .sorted(Comparator.comparingInt(SeatAvailability::getRow)
                        .thenComparingInt(SeatAvailability::getCol))
                .collect(Collectors.toList());
    }

    public List<PaymentMode> showPaymentOptions() {
        return Arrays.asList(PaymentMode.values());
    }

    public synchronized SeatHold reserveSeats(User user, String showId, List<String> seatIds) {
        cleanupExpiredSeatHolds();
        validateCustomer(user);
        validateSeatSelection(seatIds);

        Show show = requireShow(showId);
        LocalDateTime reservedAt = LocalDateTime.now();
        LocalDateTime expiresAt = reservedAt.plus(seatHoldDuration);
        String holdId = IdGenerator.newId("HLD");

        List<Seat> seatsToReserve = collectSeatsForReservation(show, seatIds, reservedAt);
        for (String seatId : seatIds) {
            show.getShowSeat(seatId).reserve(user.getId(), holdId, expiresAt, reservedAt);
        }

        SeatHold seatHold = new SeatHold(holdId, user, show, seatsToReserve, reservedAt, expiresAt);
        activeSeatHolds.put(seatHold.getId(), seatHold);
        return seatHold;
    }

    public synchronized Booking makePayment(User user, String holdId, PaymentMode paymentMode) {
        cleanupExpiredSeatHolds();
        validateCustomer(user);

        SeatHold seatHold = requireActiveSeatHold(holdId);
        validateSeatHoldOwner(user, seatHold);

        LocalDateTime now = LocalDateTime.now();
        if (seatHold.isExpired(now)) {
            expireSeatHold(seatHold);
            throw new IllegalStateException("Seat hold expired: " + holdId);
        }

        ensureSeatHoldStillValid(seatHold, now);

        double totalAmount = amountCalculator.total(seatHold.getShow(), seatHold.getSeats());
        Ticket ticket = new Ticket(IdGenerator.newId("TKT"), seatHold.getShow(), seatHold.getSeats());
        Booking booking = new Booking(IdGenerator.newId("BKG"), user, seatHold.getShow(), seatHold.getSeats(), ticket);
        booking.setTotalAmount(totalAmount);

        Payment payment = new Payment(IdGenerator.newId("PAY"), booking.getId(), paymentMode, totalAmount);
        boolean paid = paymentProcessor.pay(payment);

        if (!paid) {
            releaseSeatHold(seatHold);
            throw new IllegalStateException("Payment failed");
        }

        confirmSeatHold(seatHold, now);
        booking.setStatus(BookingStatus.CONFIRMED);
        bookings.put(booking.getId(), booking);
        paymentsByBookingId.put(booking.getId(), payment);
        return booking;
    }

    public synchronized Ticket bookTicket(User user, String showId, List<String> seatIds, PaymentMode paymentMode) {
        SeatHold seatHold = reserveSeats(user, showId, seatIds);
        Booking booking = makePayment(user, seatHold.getId(), paymentMode);
        return booking.getTicket();
    }

    public synchronized double cancelBooking(User user, String bookingId) {
        cleanupExpiredSeatHolds();
        Booking booking = requireBooking(bookingId);
        validateCancellationAccess(user, booking);

        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.REFUNDED) {
            throw new IllegalStateException("Booking already cancelled");
        }

        releaseBookedSeats(booking);

        Payment payment = requirePayment(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);
        double refundAmount = refundCalculatorFor(payment.getMode()).refund(booking);
        paymentProcessor.refund(payment);
        booking.setStatus(BookingStatus.REFUNDED);
        return refundAmount;
    }

    public void printAvailableSeats(String showId) {
        Show show = requireShow(showId);
        System.out.println("Seat map for show: " + show.getId());
        showSeatMap(showId).forEach(System.out::println);
    }

    private void validateAdmin(User user) {
        if (user == null || !user.isAdmin()) {
            throw new IllegalArgumentException("Only admin can perform this operation");
        }
    }

    private void validateCustomer(User user) {
        if (user == null || user.getRole() != UserRole.CUSTOMER) {
            throw new IllegalArgumentException("Only customer can perform this operation");
        }
    }

    private void validateCancellationAccess(User user, Booking booking) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (!booking.getUser().getId().equals(user.getId()) && !user.isAdmin()) {
            throw new IllegalArgumentException("You cannot cancel someone else's booking");
        }
    }

    private void validateSeatHoldOwner(User user, SeatHold seatHold) {
        if (!seatHold.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You cannot pay for someone else's seat hold");
        }
    }

    private void validateSeatSelection(List<String> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected");
        }
        Set<String> uniqueSeatIds = new LinkedHashSet<>(seatIds);
        if (uniqueSeatIds.size() != seatIds.size()) {
            throw new IllegalArgumentException("Duplicate seat selection is not allowed");
        }
    }

    private Movie requireMovie(String movieId) {
        Movie movie = movies.get(movieId);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found: " + movieId);
        }
        return movie;
    }

    private Theatre requireTheatre(String theatreId) {
        Theatre theatre = theatres.get(theatreId);
        if (theatre == null) {
            throw new IllegalArgumentException("Theatre not found: " + theatreId);
        }
        return theatre;
    }

    private Screen requireScreen(Theatre theatre, String screenId) {
        return theatre.getScreens()
                .stream()
                .filter(screen -> screen.getId().equals(screenId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Screen not found in given theatre"));
    }

    private Show requireShow(String showId) {
        Show show = shows.get(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }
        return show;
    }

    private Booking requireBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }
        return booking;
    }

    private Payment requirePayment(String bookingId) {
        Payment payment = paymentsByBookingId.get(bookingId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found for booking: " + bookingId);
        }
        return payment;
    }

    private SeatHold requireActiveSeatHold(String holdId) {
        SeatHold seatHold = activeSeatHolds.get(holdId);
        if (seatHold == null || !seatHold.isActive()) {
            throw new IllegalArgumentException("Active seat hold not found: " + holdId);
        }
        return seatHold;
    }

    private List<Seat> collectSeatsForReservation(Show show, List<String> seatIds, LocalDateTime now) {
        List<Seat> seatsToReserve = new ArrayList<>();
        for (String seatId : seatIds) {
            ShowSeat showSeat = show.getShowSeat(seatId);
            if (showSeat == null) {
                throw new IllegalArgumentException("Seat not found: " + seatId);
            }
            if (!showSeat.isAvailable(now)) {
                throw new IllegalStateException("Seat not available: " + seatId);
            }
            seatsToReserve.add(showSeat.getSeat());
        }
        return seatsToReserve;
    }

    private void ensureSeatHoldStillValid(SeatHold seatHold, LocalDateTime now) {
        for (Seat seat : seatHold.getSeats()) {
            ShowSeat showSeat = seatHold.getShow().getShowSeat(seat.getId());
            if (!showSeat.isReservedBy(seatHold.getUser().getId(), seatHold.getId(), now)) {
                throw new IllegalStateException("Seat hold is no longer valid for seat: " + seat.getId());
            }
        }
    }

    private void releaseBookedSeats(Booking booking) {
        for (Seat seat : booking.getSeats()) {
            booking.getShow().getShowSeat(seat.getId()).markAvailable();
        }
    }

    private void confirmSeatHold(SeatHold seatHold, LocalDateTime now) {
        for (Seat seat : seatHold.getSeats()) {
            seatHold.getShow().getShowSeat(seat.getId()).confirmBooking(seatHold.getUser().getId(), seatHold.getId(), now);
        }
        seatHold.markConfirmed();
        activeSeatHolds.remove(seatHold.getId());
    }

    private void releaseSeatHold(SeatHold seatHold) {
        for (Seat seat : seatHold.getSeats()) {
            seatHold.getShow().getShowSeat(seat.getId()).releaseReservation(seatHold.getId());
        }
        seatHold.markReleased();
        activeSeatHolds.remove(seatHold.getId());
    }

    private void expireSeatHold(SeatHold seatHold) {
        for (Seat seat : seatHold.getSeats()) {
            seatHold.getShow().getShowSeat(seat.getId()).releaseReservation(seatHold.getId());
        }
        seatHold.markExpired();
        activeSeatHolds.remove(seatHold.getId());
    }

    private void cleanupExpiredSeatHolds() {
        LocalDateTime now = LocalDateTime.now();
        List<SeatHold> expiredSeatHolds = new ArrayList<>();
        for (SeatHold seatHold : activeSeatHolds.values()) {
            if (seatHold.isActive() && seatHold.isExpired(now)) {
                expiredSeatHolds.add(seatHold);
            }
        }
        for (SeatHold seatHold : expiredSeatHolds) {
            expireSeatHold(seatHold);
        }
    }

    private RefundAmountCalculator refundCalculatorFor(PaymentMode paymentMode) {
        if (paymentMode == PaymentMode.UPI) {
            return new UpiRefundAmountCalculator();
        }
        return new CardRefundAmountCalculator();
    }
}
