import java.util.Objects;

public final class RateLimitKey {

    private final RateLimitKeyType type;
    private final String identifier;

    public RateLimitKey(RateLimitKeyType type, String identifier) {
        if (type == null || identifier == null) {
            throw new IllegalArgumentException("type and identifier cannot be null");
        }
        this.type = type;
        this.identifier = identifier;
    }

    public RateLimitKeyType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String asStorageKey() {
        return type + ":" + identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RateLimitKey)) return false;
        RateLimitKey other = (RateLimitKey) o;
        return type == other.type && identifier.equals(other.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, identifier);
    }

    @Override
    public String toString() {
        return asStorageKey();
    }
}
