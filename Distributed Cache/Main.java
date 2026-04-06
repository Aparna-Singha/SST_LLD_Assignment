public class Main {
    public static void main(String[] args) {
        InMemoryDatabase<Integer, String> database = new InMemoryDatabase<>();
        database.put(1, "User-1");
        database.put(2, "User-2");
        database.put(3, "User-3");
        database.put(4, "User-4");
        database.put(5, "User-5");

        DistributedCache<Integer, String> cache = new DistributedCache<>(
                3,
                2,
                new ModuloBasedDistributionStrategy<>(),
                LRUEvictionPolicy::new,
                database
        );

        System.out.println("=== DISTRIBUTION DEMO ===");
        System.out.println("Key 1 goes to " + cache.getResponsibleNode(1).getNodeId()); // Node-1
        System.out.println("Key 2 goes to " + cache.getResponsibleNode(2).getNodeId()); // Node-2
        System.out.println("Key 3 goes to " + cache.getResponsibleNode(3).getNodeId()); // Node-0

        System.out.println("\n=== CACHE MISS THEN DB LOAD (all 3 nodes) ===");
        System.out.println("get(1) -> " + cache.get(1)); 
        System.out.println("get(2) -> " + cache.get(2)); 
        System.out.println("get(3) -> " + cache.get(3));

        System.out.println("\n=== CACHE HIT ===");
        System.out.println("get(1) -> " + cache.get(1)); 
        System.out.println("get(2) -> " + cache.get(2)); 

        System.out.println("\n=== WRITE-THROUGH PUT ===");
        cache.put(6, "User-6"); 
        System.out.println("get(6) -> " + cache.get(6));

        System.out.println("\n=== LRU EVICTION ON NODE-1 (capacity=2) ===");
    
        cache.put(4, "User-4-updated");
        
        System.out.println("get(1) -> " + cache.get(1));
        
        cache.put(7, "User-7");
        System.out.println("get(7) -> " + cache.get(7)); 

        cache.printClusterState();

        System.out.println("\n=== DATABASE SNAPSHOT ===");
        System.out.println(database.snapshot());
    }
}
