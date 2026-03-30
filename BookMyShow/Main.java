import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {
    private static final String CITY = "Bengaluru";

    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem(
                createAmountCalculator(),
                new PaymentProcessor(),
                Duration.ofMinutes(3)
        );

        User admin = new User("U1", "Admin", "admin@bookmyshow.com", UserRole.ADMIN);
        User customer = new User("U2", "Lekhana", "lekhana@gmail.com", UserRole.CUSTOMER);

        Movie movie1 = bookingSystem.addMovie(admin, "Interstellar", 169, "English");
        Movie movie2 = bookingSystem.addMovie(admin, "Kantara", 148, "Kannada");

        Theatre theatre = bookingSystem.addTheatre(admin, "PVR Orion", CITY);
        Screen screen1 = bookingSystem.createScreen(admin, theatre, "Screen-1");
        addSampleSeats(bookingSystem, admin, screen1);

        Show morningShow = bookingSystem.addMovieShow(admin, movie1.getId(), theatre.getId(), screen1.getId(),
                LocalDateTime.of(2026, 4, 4, 9, 0));
        Show eveningShow = bookingSystem.addMovieShow(admin, movie2.getId(), theatre.getId(), screen1.getId(),
                LocalDateTime.of(2026, 4, 4, 18, 0));

        printSection("Movie-first flow");
        System.out.println("Theatres in " + CITY + ":");
        bookingSystem.showTheatre(CITY).forEach(System.out::println);
        System.out.println("\nMovies in " + CITY + ":");
        bookingSystem.showMovie(CITY).forEach(System.out::println);
        System.out.println("\nShows for movie: " + movie1.getTitle());
        bookingSystem.showShowsForMovie(CITY, movie1.getId()).forEach(System.out::println);

        printSection("Theatre-first flow");
        System.out.println("Movies in theatre: " + theatre.getName());
        bookingSystem.showMoviesInTheatre(theatre.getId()).forEach(System.out::println);
        System.out.println("\nShows in theatre for movie: " + movie2.getTitle());
        bookingSystem.showShowsInTheatre(theatre.getId(), movie2.getId()).forEach(System.out::println);

        printSection("Seat map before reservation");
        bookingSystem.showSeatMap(morningShow.getId()).forEach(System.out::println);

        SeatHold seatHold = bookingSystem.reserveSeats(customer, morningShow.getId(), Arrays.asList("A1", "B1"));
        printSection("Seat hold created");
        System.out.println("Hold ID: " + seatHold.getId());
        System.out.println("Reserved at: " + seatHold.getReservedAt());
        System.out.println("Expires at: " + seatHold.getExpiresAt());

        printSection("Seat map after reservation");
        bookingSystem.showSeatMap(morningShow.getId()).forEach(System.out::println);

        printSection("Payment options");
        bookingSystem.showPaymentOptions().forEach(System.out::println);

        Booking booking = bookingSystem.makePayment(customer, seatHold.getId(), PaymentMode.UPI);
        printSection("Booking confirmed");
        System.out.println("Booking ID: " + booking.getId());
        System.out.println("Ticket: " + booking.getTicket());
        System.out.println("Total amount: " + booking.getTotalAmount());

        printSection("Seat map after payment");
        bookingSystem.showSeatMap(morningShow.getId()).forEach(System.out::println);

        double refundAmount = bookingSystem.cancelBooking(customer, booking.getId());
        printSection("After cancellation. Refund amount: " + refundAmount);
        bookingSystem.showSeatMap(morningShow.getId()).forEach(System.out::println);

        printSection("Direct bookTicket API");
        Ticket directTicket = bookingSystem.bookTicket(customer, eveningShow.getId(), Arrays.asList("C1"), PaymentMode.CARD);
        System.out.println(directTicket);
    }

    private static RuleBasedTotalAmountCalculator createAmountCalculator() {
        RuleBasedTotalAmountCalculator amountCalculator = new RuleBasedTotalAmountCalculator();
        amountCalculator.addRule(new PricingRule(
                "Weekend surge",
                (show, seat) -> {
                    DayOfWeek dayOfWeek = show.getStartTime().getDayOfWeek();
                    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
                },
                1.2
        ));
        amountCalculator.addRule(new PricingRule(
                "Evening recliner surcharge",
                (show, seat) -> seat.getSeatType() == SeatType.RECLINER && show.getStartTime().getHour() >= 18,
                1.1
        ));
        return amountCalculator;
    }

    private static void addSampleSeats(BookingSystem bookingSystem, User admin, Screen screen) {
        bookingSystem.addSeatToScreen(admin, screen, new Seat("A1", 1, 1, SeatType.GOLD));
        bookingSystem.addSeatToScreen(admin, screen, new Seat("A2", 1, 2, SeatType.GOLD));
        bookingSystem.addSeatToScreen(admin, screen, new Seat("B1", 2, 1, SeatType.PLATINUM));
        bookingSystem.addSeatToScreen(admin, screen, new Seat("B2", 2, 2, SeatType.PLATINUM));
        bookingSystem.addSeatToScreen(admin, screen, new Seat("C1", 3, 1, SeatType.RECLINER));
    }

    private static void printSection(String title) {
        System.out.println("\n" + title);
    }
}
