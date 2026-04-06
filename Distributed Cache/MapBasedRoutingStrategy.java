import java.util.HashMap;
import java.util.Map;

public class MapBasedRoutingStrategy<K> implements DistributionStrategy<K> {
    private final Map<K, Integer> routingTable = new HashMap<>();

    public void registerRoute(K key, int nodeIndex) {
        routingTable.put(key, nodeIndex);
    }

    @Override
    public int getNodeIndex(K key, int totalNodes) {
        Integer node = routingTable.get(key);
        if (node != null) {
            return node % totalNodes;
        }
        return Math.abs(key.hashCode()) % totalNodes;
    }
}
