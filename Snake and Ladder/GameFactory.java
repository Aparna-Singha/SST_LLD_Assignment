import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameFactory {
    private GameFactory() {
    }

    public static Game createEasyGame(String... playerNames) {
        return createEasyGame(Arrays.asList(playerNames));
    }

    public static Game createEasyGame(List<String> playerNames) {
        return createGame(playerNames, new EasyMove());
    }

    public static Game createHardGame(String... playerNames) {
        return createHardGame(Arrays.asList(playerNames));
    }

    public static Game createHardGame(List<String> playerNames) {
        return createGame(playerNames, new HardMove());
    }

    public static Game createGame(List<String> playerNames, IMakeMove moveStrategy) {
        if (playerNames == null || playerNames.isEmpty()) {
            throw new IllegalArgumentException("Player list cannot be empty.");
        }

        List<Player> players = new ArrayList<>();

        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }

        return new Game(BoardFactory.createStandardBoard(), new Dice(), moveStrategy, players);
    }
}
