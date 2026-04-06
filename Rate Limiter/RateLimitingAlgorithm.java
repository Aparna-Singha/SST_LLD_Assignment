public interface RateLimitingAlgorithm {

    RateLimitDecision evaluate(RateLimitKey key, RateLimitPolicy policy);

    String name();
}
