import java.util.Random;

public class Dice {
    private final int sides;
    private final Random random;

    public Dice() {
        this(6);
    }

    public Dice(int sides) {
        this(sides, new Random());
    }

    public Dice(int sides, Random random) {
        if (sides <= 0) {
            throw new IllegalArgumentException("Dice must have at least one side.");
        }

        if (random == null) {
            throw new IllegalArgumentException("Random generator cannot be null.");
        }

        this.sides = sides;
        this.random = random;
    }

    public int getSides() {
        return sides;
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }
}
