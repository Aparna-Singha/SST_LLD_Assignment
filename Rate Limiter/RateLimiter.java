public interface RateLimiter {

    RateLimitDecision tryAcquire(RateLimitKey key, RateLimitPolicy policy);

    String getAlgorithmName();
}
