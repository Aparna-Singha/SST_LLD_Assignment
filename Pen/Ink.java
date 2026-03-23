public class Ink {
    private final InkColor color;
    private final int capacity;
    private int currentLevel;

    public Ink(InkColor color, int capacity, int currentLevel) {
        if (color == null) {
            throw new IllegalArgumentException("Ink color cannot be null.");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Ink capacity must be greater than 0.");
        }

        if (currentLevel < 0 || currentLevel > capacity) {
            throw new IllegalArgumentException("Ink level must be between 0 and capacity.");
        }

        this.color = color;
        this.capacity = capacity;
        this.currentLevel = currentLevel;
    }

    public InkColor getColor() {
        return color;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean isEmpty() {
        return currentLevel == 0;
    }

    public void consume(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Ink consumption must be greater than 0.");
        }

        if (amount > currentLevel) {
            throw new IllegalStateException("Ink level is not enough for this operation.");
        }

        currentLevel -= amount;
    }

    public void refill(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Refill amount must be greater than 0.");
        }

        currentLevel = Math.min(capacity, currentLevel + amount);
    }
}
