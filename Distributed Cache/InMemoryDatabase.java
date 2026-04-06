import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase<K, V> implements Database<K, V> {
    private final Map<K, V> store = new HashMap<>();

    @Override
    public V get(K key) {
        return store.get(key);
    }

    @Override
    public void put(K key, V value) {
        store.put(key, value);
    }

    public Map<K, V> snapshot() {
        return new HashMap<>(store);
    }
}
