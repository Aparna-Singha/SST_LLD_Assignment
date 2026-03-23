public class Snake implements IBoardEntity {
    private final int start;
    private final int end;

    public Snake(int start, int end) {
        if (start <= end) {
            throw new IllegalArgumentException("A snake must move a player downward.");
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
        return "Snake{start=" + start + ", end=" + end + "}";
    }
}
