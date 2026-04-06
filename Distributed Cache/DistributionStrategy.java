public interface DistributionStrategy<K> {
    int getNodeIndex(K key, int totalNodes);
}
