public class HardMove implements IMakeMove {
    @Override
    public int makeMove(Player player, int diceValue, Board board) {
        int nextPosition = player.getPosition() + diceValue;

        if (nextPosition > board.getSize()) {
            int overshoot = nextPosition - board.getSize();
            nextPosition = board.getSize() - overshoot;
        }

        return board.resolvePosition(nextPosition);
    }
}
