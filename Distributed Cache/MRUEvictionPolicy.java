import java.util.LinkedHashSet;

public class MRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final LinkedHashSet<K> order = new LinkedHashSet<>();

    @Override
    public void recordAccess(K key) {
        order.remove(key);
        order.add(key);
    }

    @Override
    public K getEvictKey() {
        K last = null;
        for (K k : order) last = k; // most recently used is last
        return last;
    }

    @Override
    public void remove(K key) {
        order.remove(key);
    }
}
