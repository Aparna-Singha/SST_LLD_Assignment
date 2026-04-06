public class DefaultRateLimiter implements RateLimiter {

    private final RateLimitingAlgorithm algorithm;

    public DefaultRateLimiter(RateLimitingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public RateLimitDecision tryAcquire(RateLimitKey key, RateLimitPolicy policy) {
        return algorithm.evaluate(key, policy);
    }

    @Override
    public String getAlgorithmName() {
        return algorithm.name();
    }
}
