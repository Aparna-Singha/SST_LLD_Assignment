import java.util.LinkedHashSet;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final LinkedHashSet<K> order = new LinkedHashSet<>();

    @Override
    public void recordAccess(K key) {
        order.remove(key);
        order.add(key); // most recent goes to end
    }

    @Override
    public K getEvictKey() {
        return order.iterator().next(); // first element = least recently used
    }

    @Override
    public void remove(K key) {
        order.remove(key);
    }
}
