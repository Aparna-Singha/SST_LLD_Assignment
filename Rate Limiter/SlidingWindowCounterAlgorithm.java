import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounterAlgorithm extends AbstractRateLimitingAlgorithm {

    private final ConcurrentHashMap<RateLimitKey, Map<LimitRule, SlidingState>> stateByKey;

    public SlidingWindowCounterAlgorithm() {
        this.stateByKey = new ConcurrentHashMap<>();
    }

    public SlidingWindowCounterAlgorithm(Clock clock) {
        super(clock);
        this.stateByKey = new ConcurrentHashMap<>();
    }

    @Override
    public String name() {
        return "SlidingWindowCounter";
    }

    @Override
    protected RateLimitDecision doEvaluate(RateLimitKey key, RateLimitPolicy policy, long nowMillis) {
        Map<LimitRule, SlidingState> ruleStates = stateByKey.computeIfAbsent(key, k -> new HashMap<>());

        for (LimitRule rule : policy.getRules()) {
            SlidingState state = ruleStates.computeIfAbsent(rule,
                    r -> SlidingState.fresh(nowMillis, r.getWindowMillis()));

            state.advanceIfNeeded(nowMillis, rule.getWindowMillis());

            double estimated = state.estimatedCount(nowMillis, rule.getWindowMillis());
            if (estimated + 1 > rule.getMaxRequests()) {
                long elapsed = nowMillis - state.currentWindowStart;
                long retryAfter = rule.getWindowMillis() - elapsed;
                return RateLimitDecision.denied(
                        name(),
                        "Limit exceeded for rule: " + rule.getName(),
                        rule,
                        Math.max(retryAfter, 0L)
                );
            }
        }

        long minRemaining = Long.MAX_VALUE;
        for (LimitRule rule : policy.getRules()) {
            SlidingState state = ruleStates.get(rule);
            state.currentCount++;
            double estimated = state.estimatedCount(nowMillis, rule.getWindowMillis());
            long remaining = Math.max(0L, (long) Math.floor(rule.getMaxRequests() - estimated));
            minRemaining = Math.min(minRemaining, remaining);
        }

        return RateLimitDecision.allowed(name(), "Request allowed", minRemaining);
    }

    private static class SlidingState {
        long currentWindowStart;
        int currentCount;
        int previousCount;

        SlidingState(long currentWindowStart, int currentCount, int previousCount) {
            this.currentWindowStart = currentWindowStart;
            this.currentCount = currentCount;
            this.previousCount = previousCount;
        }

        static SlidingState fresh(long nowMillis, long windowSizeMillis) {
            long start = nowMillis - Math.floorMod(nowMillis, windowSizeMillis);
            return new SlidingState(start, 0, 0);
        }

        void advanceIfNeeded(long nowMillis, long windowSizeMillis) {
            long newWindowStart = nowMillis - Math.floorMod(nowMillis, windowSizeMillis);
            if (newWindowStart == currentWindowStart) return;

            long windowsElapsed = (newWindowStart - currentWindowStart) / windowSizeMillis;
            previousCount = (windowsElapsed == 1) ? currentCount : 0;
            currentCount = 0;
            currentWindowStart = newWindowStart;
        }

        double estimatedCount(long nowMillis, long windowSizeMillis) {
            long elapsedInCurrent = nowMillis - currentWindowStart;
            double prevWeight = 1.0 - ((double) elapsedInCurrent / windowSizeMillis);
            prevWeight = Math.max(0.0, Math.min(prevWeight, 1.0));
            return currentCount + (previousCount * prevWeight);
        }
    }
}
