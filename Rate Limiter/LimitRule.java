import java.time.Duration;

public final class LimitRule {

    private final String name;
    private final int maxRequests;
    private final Duration window;

    public LimitRule(String name, int maxRequests, Duration window) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("maxRequests has to be positive, got: " + maxRequests);
        }
        if (window == null || window.isZero() || window.isNegative()) {
            throw new IllegalArgumentException("window must be a positive duration");
        }
        this.name = name;
        this.maxRequests = maxRequests;
        this.window = window;
    }

    public String getName() {
        return name;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public Duration getWindow() {
        return window;
    }

    public long getWindowMillis() {
        return window.toMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LimitRule)) return false;
        LimitRule other = (LimitRule) o;
        return maxRequests == other.maxRequests
                && name.equals(other.name)
                && window.equals(other.window);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + maxRequests;
        result = 31 * result + window.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + " (" + maxRequests + " req / " + window + ")";
    }
}
