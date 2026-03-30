public class ExternalRequest extends Request {
    private final Direction direction;

    public ExternalRequest(int sourceFloor, Direction direction) {
        super(sourceFloor);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "ExternalRequest{sourceFloor=" + sourceFloor + ", direction=" + direction + '}';
    }
}
