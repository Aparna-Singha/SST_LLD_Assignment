public class EasyMove implements IMakeMove {
    @Override
    public int makeMove(Player player, int diceValue, Board board) {
        int nextPosition = player.getPosition() + diceValue;

        if (nextPosition > board.getSize()) {
            return player.getPosition();
        }

        return board.resolvePosition(nextPosition);
    }
}
