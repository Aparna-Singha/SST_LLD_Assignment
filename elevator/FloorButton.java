public class FloorButton extends Button {
    private final Direction direction;

    public FloorButton(Direction direction) {
        super(direction == Direction.UP ? ButtonType.FLOOR_UP : ButtonType.FLOOR_DOWN);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
