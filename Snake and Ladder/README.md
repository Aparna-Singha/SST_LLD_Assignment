# Snake and Ladder

## Brief Explanation

This project is a Java implementation of Snake and Ladder built with simple object-oriented classes.

Current behavior in the code:

- The user enters `n`, player count, and difficulty level.
- The board contains cells from `1` to `n^2`.
- `BoardFactory` creates `n` snakes and `n` ladders randomly.
- Players start outside the board at position `0`.
- A six-sided dice generates random moves.
- `Snake` moves a player down and `Ladder` moves a player up.
- `EasyMove` keeps the player in the same place if the dice roll crosses the last cell.
- `HardMove` bounces the player back from the last cell if the dice roll crosses it.
- The board rejects snake and ladder placements that would create a cycle.
- Winners leave the game, and play continues until fewer than two players are still trying to win.

## Main Classes

- `Board`: stores total cell count and all snakes/ladders.
- `IBoardEntity`: common contract for snake and ladder positions.
- `Snake`: represents a downward move.
- `Ladder`: represents an upward move.
- `Player`: stores player name and current position.
- `Dice`: rolls a random number from `1` to `6`.
- `IMakeMove`: strategy interface for movement rules.
- `EasyMove`: exact-finish movement rule.
- `HardMove`: bounce-back movement rule.
- `Game`: runs turn-by-turn gameplay and tracks winners.
- `BoardFactory`: creates valid random boards.
- `GameFactory`: creates ready-to-play games from user inputs.

## UML Diagram

```mermaid
classDiagram
    class Board {
        -int size
        -Map~Integer, IBoardEntity~ entities
        +Board(int size)
        +getSize() int
        +addEntity(IBoardEntity entity) void
        +hasEntityAt(int position) boolean
        +getEntityAt(int position) IBoardEntity
        +resolvePosition(int position) int
        +isWinningPosition(int position) boolean
    }

    class IBoardEntity {
        <<interface>>
        +getStart() int
        +getEnd() int
        +moveTo() int
    }

    class Snake {
        -int start
        -int end
        +Snake(int start, int end)
        +getStart() int
        +getEnd() int
    }

    class Ladder {
        -int start
        -int end
        +Ladder(int start, int end)
        +getStart() int
        +getEnd() int
    }

    class Player {
        -String name
        -int position
        +Player(String name)
        +getName() String
        +getPosition() int
        +setPosition(int position) void
        +reset() void
    }

    class Dice {
        -int sides
        -Random random
        +Dice()
        +Dice(int sides)
        +roll() int
    }

    class IMakeMove {
        <<interface>>
        +makeMove(Player player, int diceValue, Board board) int
    }

    class EasyMove {
        +makeMove(Player player, int diceValue, Board board) int
    }

    class HardMove {
        +makeMove(Player player, int diceValue, Board board) int
    }

    class Game {
        -Board board
        -Dice dice
        -IMakeMove moveStrategy
        -List~Player~ players
        -List~Player~ activePlayers
        -List~Player~ winners
        -int turnIndex
        +Game(Board board, Dice dice, IMakeMove moveStrategy, List~Player~ players)
        +getActivePlayers() List~Player~
        +getWinners() List~Player~
        +isGameOver() boolean
        +playTurn() void
        +play() List~Player~
        +reset() void
    }

    class BoardFactory {
        +createRandomBoard(int dimension) Board
        +createBoard(int size, List~Snake~ snakes, List~Ladder~ ladders) Board
    }

    class GameFactory {
        +createGame(int boardDimension, int playerCount, String difficultyLevel) Game
    }

    IBoardEntity <|.. Snake
    IBoardEntity <|.. Ladder
    IMakeMove <|.. EasyMove
    IMakeMove <|.. HardMove
    Board --> IBoardEntity
    Game --> Board
    Game --> Dice
    Game --> Player
    Game --> IMakeMove
    BoardFactory ..> Board
    BoardFactory ..> Snake
    BoardFactory ..> Ladder
    GameFactory ..> Game
    GameFactory ..> BoardFactory
    GameFactory ..> EasyMove
    GameFactory ..> HardMove
```

## How to Run

Compile:

```bash
javac *.java
```

Run:

```bash
java Game
```
