import java.time.Duration;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        RateLimitPolicy policy = new RateLimitPolicy(
                "ExternalProviderPolicy",
                List.of(new LimitRule("5 per 10 seconds", 5, Duration.ofSeconds(10)))
        );

        RateLimitKey t1 = new RateLimitKey(RateLimitKeyType.TENANT, "T1");
        ExternalResourceClient externalClient = new DemoExternalResourceClient();

        System.out.println("=== Fixed Window Counter ===");
        InternalService fixedWindowService = new InternalService(
                "BillingService",
                new DefaultRateLimiter(new FixedWindowCounterAlgorithm()),
                externalClient,
                policy
        );
        runScenario(fixedWindowService, t1);

        System.out.println("\nWaiting for window to reset...\n");
        Thread.sleep(11000);

        System.out.println("=== Sliding Window Counter ===");
        InternalService slidingWindowService = new InternalService(
                "BillingService",
                new DefaultRateLimiter(new SlidingWindowCounterAlgorithm()),
                externalClient,
                policy
        );
        runScenario(slidingWindowService, t1);
    }

    private static void runScenario(InternalService service, RateLimitKey key) {
        System.out.println(service.handleRequest("req-1", false, key));

        for (int i = 2; i <= 6; i++) {
            System.out.println(service.handleRequest("req-" + i, true, key));
        }

        System.out.println(service.handleRequest("req-7", true, key));
    }
}
