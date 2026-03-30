public class Movie {
    private final String id;
    private final String title;
    private final int durationInMinutes;
    private final String language;

    public Movie(String id, String title, int durationInMinutes, String language) {
        this.id = id;
        this.title = title;
        this.durationInMinutes = durationInMinutes;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Movie{id='" + id + "', title='" + title + "', duration=" + durationInMinutes + ", language='" + language + "'}";
    }
}
