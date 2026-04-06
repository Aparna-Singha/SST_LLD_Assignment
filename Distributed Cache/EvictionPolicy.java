public interface EvictionPolicy<K> {
    void recordAccess(K key);
    K getEvictKey();
    void remove(K key);
}
