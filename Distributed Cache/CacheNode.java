import java.util.HashMap;
import java.util.Map;

public class CacheNode<K, V> {
    private final String nodeId;
    private final int capacity;
    private final EvictionPolicy<K> evictionPolicy;
    private final Map<K, V> store = new HashMap<>();

    public CacheNode(String nodeId, int capacity, EvictionPolicy<K> evictionPolicy) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
    }

    public V get(K key) {
        if (!store.containsKey(key)) return null;
        evictionPolicy.recordAccess(key);
        return store.get(key);
    }

    public void put(K key, V value) {
        if (store.containsKey(key)) {
            store.put(key, value);
            evictionPolicy.recordAccess(key);
            return;
        }
        if (store.size() >= capacity) {
            K toEvict = evictionPolicy.getEvictKey();
            store.remove(toEvict);
            evictionPolicy.remove(toEvict);
            System.out.println(nodeId + " evicted key: " + toEvict);
        }
        store.put(key, value);
        evictionPolicy.recordAccess(key);
    }

    public boolean containsKey(K key) {
        return store.containsKey(key);
    }

    public String getNodeId() {
        return nodeId;
    }

    public Map<K, V> getStore() {
        return store;
    }
}
