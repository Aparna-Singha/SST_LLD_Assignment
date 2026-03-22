public class Ladder implements IBoardEntity {
    private final int start;
    private final int end;

    public Ladder(int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException("A ladder must move a player upward.");
        }

        this.start = start;
        this.end = end;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Ladder{start=" + start + ", end=" + end + "}";
    }
}
