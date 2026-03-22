import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    private final int size;
    private final Map<Integer, IBoardEntity> entities;

    public Board(int size) {
        if (size <= 1) {
            throw new IllegalArgumentException("Board size must be greater than 1.");
        }

        this.size = size;
        this.entities = new HashMap<>();
    }

    public int getSize() {
        return size;
    }

    public void addEntity(IBoardEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Board entity cannot be null.");
        }

        int start = entity.getStart();
        int end = entity.getEnd();

        if (start <= 0 || start >= size) {
            throw new IllegalArgumentException("Entity start must be between 1 and " + (size - 1) + ".");
        }

        if (end <= 0 || end > size) {
            throw new IllegalArgumentException("Entity end must be between 1 and " + size + ".");
        }

        if (start == end) {
            throw new IllegalArgumentException("Entity start and end cannot be the same.");
        }

        if (entities.containsKey(start)) {
            throw new IllegalArgumentException("Another entity already starts at square " + start + ".");
        }

        entities.put(start, entity);
    }

    public boolean hasEntityAt(int position) {
        return entities.containsKey(position);
    }

    public IBoardEntity getEntityAt(int position) {
        return entities.get(position);
    }

    public int resolvePosition(int position) {
        if (position < 0 || position > size) {
            throw new IllegalArgumentException("Position must be between 0 and " + size + ".");
        }

        int currentPosition = position;
        Set<Integer> visited = new HashSet<>();

        while (entities.containsKey(currentPosition)) {
            if (!visited.add(currentPosition)) {
                throw new IllegalStateException("Board contains a movement cycle at square " + currentPosition + ".");
            }

            currentPosition = entities.get(currentPosition).moveTo();
        }

        return currentPosition;
    }

    public boolean isWinningPosition(int position) {
        return position == size;
    }
}
