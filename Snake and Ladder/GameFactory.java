import java.util.ArrayList;
import java.util.List;

public class GameFactory {
    private GameFactory() {
    }

    public static Game createGame(int boardDimension, int playerCount, String difficultyLevel) {
        if (playerCount < 2) {
            throw new IllegalArgumentException("At least two players are required.");
        }

        List<Player> players = createPlayers(playerCount);
        IMakeMove moveStrategy = createMoveStrategy(difficultyLevel);
        Board board = BoardFactory.createRandomBoard(boardDimension);

        return new Game(board, new Dice(), moveStrategy, players);
    }

    private static List<Player> createPlayers(int playerCount) {
        List<Player> players = new ArrayList<>();

        for (int playerNumber = 1; playerNumber <= playerCount; playerNumber++) {
            players.add(new Player("Player-" + playerNumber));
        }

        return players;
    }

    private static IMakeMove createMoveStrategy(String difficultyLevel) {
        if (difficultyLevel == null) {
            throw new IllegalArgumentException("Difficulty level cannot be null.");
        }

        String normalizedDifficulty = difficultyLevel.trim().toLowerCase();

        if ("easy".equals(normalizedDifficulty)) {
            return new EasyMove();
        }

        if ("hard".equals(normalizedDifficulty)) {
            return new HardMove();
        }

        throw new IllegalArgumentException("Difficulty level must be either easy or hard.");
    }
}