import java.util.HashMap;
import java.util.Map;

public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Map<K, Integer> freqMap = new HashMap<>();
    private final Map<K, Long> insertionOrder = new HashMap<>();
    private long counter = 0;

    @Override
    public void recordAccess(K key) {
        freqMap.put(key, freqMap.getOrDefault(key, 0) + 1);
        if (!insertionOrder.containsKey(key)) {
            insertionOrder.put(key, counter++);
        }
    }

    @Override
    public K getEvictKey() {
        K victim = null;
        int minFreq = Integer.MAX_VALUE;
        long oldest = Long.MAX_VALUE;

        for (K key : freqMap.keySet()) {
            int freq = freqMap.get(key);
            long order = insertionOrder.get(key);
            if (freq < minFreq || (freq == minFreq && order < oldest)) {
                victim = key;
                minFreq = freq;
                oldest = order;
            }
        }
        return victim;
    }

    @Override
    public void remove(K key) {
        freqMap.remove(key);
        insertionOrder.remove(key);
    }
}
