import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {
    private final Board board;
    private final Dice dice;
    private final IMakeMove moveStrategy;
    private final List<Player> players;
    private Player winner;
    private int turnIndex;

    public Game(Board board, Dice dice, IMakeMove moveStrategy, List<Player> players) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }

        if (dice == null) {
            throw new IllegalArgumentException("Dice cannot be null.");
        }

        if (moveStrategy == null) {
            throw new IllegalArgumentException("Move strategy cannot be null.");
        }

        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("At least one player is required.");
        }

        this.board = board;
        this.dice = dice;
        this.moveStrategy = moveStrategy;
        this.players = new ArrayList<>(players);
        this.turnIndex = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Player getWinner() {
        return winner;
    }

    public boolean hasWinner() {
        return winner != null;
    }

    public void playTurn(Player player) {
        if (winner != null) {
            throw new IllegalStateException("The game is already over. Winner: " + winner.getName());
        }

        int previousPosition = player.getPosition();
        int diceValue = dice.roll();
        int newPosition = moveStrategy.makeMove(player, diceValue, board);
        player.setPosition(newPosition);

        System.out.println(
            player.getName() + " rolled " + diceValue + " and moved from " + previousPosition + " to " + newPosition + "."
        );

        if (board.isWinningPosition(newPosition)) {
            winner = player;
            System.out.println(player.getName() + " wins the game.");
        }
    }

    public Player play() {
        while (winner == null) {
            Player currentPlayer = players.get(turnIndex);
            playTurn(currentPlayer);
            turnIndex = (turnIndex + 1) % players.size();
        }

        return winner;
    }

    public void reset() {
        winner = null;
        turnIndex = 0;

        for (Player player : players) {
            player.reset();
        }
    }

    public static void main(String[] args) {
        List<String> playerNames;

        if (args.length == 0) {
            playerNames = Arrays.asList("Player-1", "Player-2");
        } else {
            playerNames = Arrays.asList(args);
        }

        Game game = GameFactory.createEasyGame(playerNames);
        game.play();
    }
}
