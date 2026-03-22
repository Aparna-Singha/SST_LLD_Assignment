public class Player {
    private final String name;
    private int position;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty.");
        }

        this.name = name.trim();
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Player position cannot be negative.");
        }

        this.position = position;
    }

    public void reset() {
        position = 0;
    }
}
