import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DistributedCache<K, V> {
    private final List<CacheNode<K, V>> nodes = new ArrayList<>();
    private final DistributionStrategy<K> distributionStrategy;
    private final Database<K, V> database;

    public DistributedCache(int nodeCount, int nodeCapacity,
                            DistributionStrategy<K> distributionStrategy,
                            Supplier<EvictionPolicy<K>> evictionPolicySupplier,
                            Database<K, V> database) {
        this.distributionStrategy = distributionStrategy;
        this.database = database;
        for (int i = 0; i < nodeCount; i++) {
            nodes.add(new CacheNode<>("Node-" + i, nodeCapacity, evictionPolicySupplier.get()));
        }
    }

    public V get(K key) {
        CacheNode<K, V> node = getResponsibleNode(key);
        V value = node.get(key);
        if (value != null || node.containsKey(key)) {
            System.out.println("Cache HIT for key=" + key + " on " + node.getNodeId());
            return value;
        }
        System.out.println("Cache MISS for key=" + key + " on " + node.getNodeId() + " -> loading from DB");
        V dbValue = database.get(key);
        if (dbValue != null) {
            node.put(key, dbValue);
        }
        return dbValue;
    }

    public void put(K key, V value) {
        CacheNode<K, V> node = getResponsibleNode(key);
        node.put(key, value);
        database.put(key, value);
        System.out.println("PUT key=" + key + " on " + node.getNodeId());
    }

    public CacheNode<K, V> getResponsibleNode(K key) {
        int index = distributionStrategy.getNodeIndex(key, nodes.size());
        return nodes.get(index);
    }

    public void printClusterState() {
        System.out.println("\nCluster state:");
        for (CacheNode<K, V> node : nodes) {
            System.out.println(node.getNodeId() + " -> " + node.getStore());
        }
    }
}
