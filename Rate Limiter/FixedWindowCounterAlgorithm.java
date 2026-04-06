import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowCounterAlgorithm extends AbstractRateLimitingAlgorithm {

    private final ConcurrentHashMap<RateLimitKey, Map<LimitRule, WindowState>> stateByKey;

    public FixedWindowCounterAlgorithm() {
        this.stateByKey = new ConcurrentHashMap<>();
    }

    public FixedWindowCounterAlgorithm(Clock clock) {
        super(clock);
        this.stateByKey = new ConcurrentHashMap<>();
    }

    @Override
    public String name() {
        return "FixedWindowCounter";
    }

    @Override
    protected RateLimitDecision doEvaluate(RateLimitKey key, RateLimitPolicy policy, long nowMillis) {
        Map<LimitRule, WindowState> ruleStates = stateByKey.computeIfAbsent(key, k -> new HashMap<>());

        for (LimitRule rule : policy.getRules()) {
            WindowState state = ruleStates.computeIfAbsent(rule,
                    r -> new WindowState(alignedWindowStart(nowMillis, r.getWindowMillis())));

            state.advanceIfExpired(nowMillis, rule.getWindowMillis());

            if (state.count + 1 > rule.getMaxRequests()) {
                long retryAfter = (state.windowStart + rule.getWindowMillis()) - nowMillis;
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
            WindowState state = ruleStates.get(rule);
            state.count++;
            long remaining = rule.getMaxRequests() - state.count;
            minRemaining = Math.min(minRemaining, remaining);
        }

        return RateLimitDecision.allowed(name(), "Request allowed", minRemaining);
    }

    private long alignedWindowStart(long nowMillis, long windowSizeMillis) {
        return nowMillis - Math.floorMod(nowMillis, windowSizeMillis);
    }

    private static class WindowState {
        long windowStart;
        int count;

        WindowState(long windowStart) {
            this.windowStart = windowStart;
            this.count = 0;
        }

        void advanceIfExpired(long nowMillis, long windowSizeMillis) {
            long currentWindowStart = nowMillis - Math.floorMod(nowMillis, windowSizeMillis);
            if (currentWindowStart != windowStart) {
                windowStart = currentWindowStart;
                count = 0;
            }
        }
    }
}
