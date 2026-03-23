import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Dice dice;
    private final IMakeMove moveStrategy;
    private final List<Player> players;
    private final List<Player> activePlayers;
    private final List<Player> winners;
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

        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least two players are required.");
        }

        this.board = board;
        this.dice = dice;
        this.moveStrategy = moveStrategy;
        this.players = new ArrayList<>(players);
        this.activePlayers = new ArrayList<>(players);
        this.winners = new ArrayList<>();
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

    public List<Player> getActivePlayers() {
        return Collections.unmodifiableList(activePlayers);
    }

    public List<Player> getWinners() {
        return Collections.unmodifiableList(winners);
    }

    public boolean isGameOver() {
        return activePlayers.size() < 2;
    }

    public void playTurn() {
        if (isGameOver()) {
            throw new IllegalStateException("The game is already over.");
        }

        Player player = activePlayers.get(turnIndex);
        int previousPosition = player.getPosition();
        int diceValue = dice.roll();
        int newPosition = moveStrategy.makeMove(player, diceValue, board);
        player.setPosition(newPosition);

        System.out.println(
            player.getName() + " rolled " + diceValue + " and moved from " + previousPosition + " to " + newPosition + "."
        );

        if (board.isWinningPosition(newPosition)) {
            winners.add(player);
            activePlayers.remove(turnIndex);
            System.out.println(player.getName() + " reached the last cell and won.");

            if (!activePlayers.isEmpty() && turnIndex >= activePlayers.size()) {
                turnIndex = 0;
            }
        } else {
            turnIndex = (turnIndex + 1) % activePlayers.size();
        }
    }

    public List<Player> play() {
        while (!isGameOver()) {
            playTurn();
        }

        if (!activePlayers.isEmpty()) {
            System.out.println(activePlayers.get(0).getName() + " is the last player still trying to win.");
        }

        return new ArrayList<>(winners);
    }

    public void reset() {
        turnIndex = 0;
        winners.clear();
        activePlayers.clear();
        activePlayers.addAll(players);

        for (Player player : players) {
            player.reset();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int boardDimension = readIntAtLeast(scanner, "Enter board size n: ", 3);
        int playerCount = readIntAtLeast(scanner, "Enter number of players: ", 2);
        String difficultyLevel = readDifficultyLevel(scanner);

        Game game = GameFactory.createGame(boardDimension, playerCount, difficultyLevel);
        List<Player> winningOrder = game.play();

        if (!winningOrder.isEmpty()) {
            System.out.println("Winning order:");

            for (int index = 0; index < winningOrder.size(); index++) {
                System.out.println((index + 1) + ". " + winningOrder.get(index).getName());
            }
        }
    }

    private static int readIntAtLeast(Scanner scanner, String prompt, int minimumValue) {
        while (true) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();

                if (value >= minimumValue) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }

            System.out.println("Please enter a number greater than or equal to " + minimumValue + ".");
        }
    }

    private static String readDifficultyLevel(Scanner scanner) {
        while (true) {
            System.out.print("Enter difficulty level (easy/hard): ");
            String difficultyLevel = scanner.nextLine().trim().toLowerCase();

            if ("easy".equals(difficultyLevel) || "hard".equals(difficultyLevel)) {
                return difficultyLevel;
            }

            System.out.println("Please enter either easy or hard.");
        }
    }
}
