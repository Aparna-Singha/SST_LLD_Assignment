import java.util.List;
import java.util.Random;

public class BoardFactory {
    private static final int MAX_BOARD_ATTEMPTS = 250;

    private BoardFactory() {
    }

    public static Board createRandomBoard(int dimension) {
        if (dimension < 3) {
            throw new IllegalArgumentException("Board size n must be at least 3.");
        }

        int totalCells = Math.multiplyExact(dimension, dimension);
        Random random = new Random();

        for (int boardAttempt = 0; boardAttempt < MAX_BOARD_ATTEMPTS; boardAttempt++) {
            Board board = new Board(totalCells);

            if (placeSnakes(board, dimension, random) && placeLadders(board, dimension, random)) {
                return board;
            }
        }

        throw new IllegalStateException("Unable to generate a valid board for n = " + dimension + ".");
    }

    public static Board createBoard(int size, List<Snake> snakes, List<Ladder> ladders) {
        Board board = new Board(size);

        for (Snake snake : snakes) {
            board.addEntity(snake);
        }

        for (Ladder ladder : ladders) {
            board.addEntity(ladder);
        }

        return board;
    }

    private static boolean placeSnakes(Board board, int snakeCount, Random random) {
        int remaining = snakeCount;
        int attempts = 0;
        int maxAttempts = board.getSize() * board.getSize();

        while (remaining > 0 && attempts < maxAttempts) {
            int head = randomInRange(random, 2, board.getSize() - 1);
            int tail = randomInRange(random, 1, head - 1);

            if (board.hasEntityAt(head)) {
                attempts++;
                continue;
            }

            try {
                board.addEntity(new Snake(head, tail));
                remaining--;
            } catch (IllegalArgumentException exception) {
                // Retry with another random position.
            }

            attempts++;
        }

        return remaining == 0;
    }

    private static boolean placeLadders(Board board, int ladderCount, Random random) {
        int remaining = ladderCount;
        int attempts = 0;
        int maxAttempts = board.getSize() * board.getSize();

        while (remaining > 0 && attempts < maxAttempts) {
            int start = randomInRange(random, 1, board.getSize() - 1);
            int end = randomInRange(random, start + 1, board.getSize());

            if (board.hasEntityAt(start)) {
                attempts++;
                continue;
            }

            try {
                board.addEntity(new Ladder(start, end));
                remaining--;
            } catch (IllegalArgumentException exception) {
                // Retry with another random position.
            }

            attempts++;
        }

        return remaining == 0;
    }

    private static int randomInRange(Random random, int minValue, int maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("Invalid range for random value generation.");
        }

        return random.nextInt(maxValue - minValue + 1) + minValue;
    }
}
