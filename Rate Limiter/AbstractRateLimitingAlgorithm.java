import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractRateLimitingAlgorithm implements RateLimitingAlgorithm {

    private final Clock clock;
    private final ConcurrentHashMap<RateLimitKey, ReentrantLock> keyLocks = new ConcurrentHashMap<>();

    protected AbstractRateLimitingAlgorithm() {
        this(Clock.systemUTC());
    }

    protected AbstractRateLimitingAlgorithm(Clock clock) {
        this.clock = clock;
    }

    @Override
    public final RateLimitDecision evaluate(RateLimitKey key, RateLimitPolicy policy) {
        ReentrantLock lock = keyLocks.computeIfAbsent(key, k -> new ReentrantLock());
        lock.lock();
        try {
            long now = Instant.now(clock).toEpochMilli();
            return doEvaluate(key, policy, now);
        } finally {
            lock.unlock();
        }
    }

    protected abstract RateLimitDecision doEvaluate(RateLimitKey key, RateLimitPolicy policy, long nowMillis);
}
