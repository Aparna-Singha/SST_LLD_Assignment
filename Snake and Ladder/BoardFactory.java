import java.util.Arrays;
import java.util.List;

public class BoardFactory {
    private BoardFactory() {
    }

    public static Board createStandardBoard() {
        return createBoard(100, defaultSnakes(), defaultLadders());
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

    private static List<Snake> defaultSnakes() {
        return Arrays.asList(
            new Snake(17, 7),
            new Snake(54, 34),
            new Snake(62, 19),
            new Snake(64, 60),
            new Snake(87, 24),
            new Snake(93, 73),
            new Snake(95, 75),
            new Snake(99, 78)
        );
    }

    private static List<Ladder> defaultLadders() {
        return Arrays.asList(
            new Ladder(4, 14),
            new Ladder(9, 31),
            new Ladder(20, 38),
            new Ladder(28, 84),
            new Ladder(40, 59),
            new Ladder(51, 67),
            new Ladder(63, 81),
            new Ladder(71, 91)
        );
    }
}
