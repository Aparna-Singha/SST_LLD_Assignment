public final class RateLimitDecision {

    private final boolean allowed;
    private final String algorithm;
    private final String reason;
    private final LimitRule violatedRule;
    private final long retryAfterMillis;
    private final long remainingRequests;

    private RateLimitDecision(boolean allowed, String algorithm, String reason,
                               LimitRule violatedRule, long retryAfterMillis, long remainingRequests) {
        this.allowed = allowed;
        this.algorithm = algorithm;
        this.reason = reason;
        this.violatedRule = violatedRule;
        this.retryAfterMillis = retryAfterMillis;
        this.remainingRequests = remainingRequests;
    }

    public static RateLimitDecision allowed(String algorithm, String reason, long remaining) {
        return new RateLimitDecision(true, algorithm, reason, null, 0L, remaining);
    }

    public static RateLimitDecision denied(String algorithm, String reason,
                                           LimitRule violated, long retryAfterMillis) {
        return new RateLimitDecision(false, algorithm, reason, violated, retryAfterMillis, 0L);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getMessage() {
        return reason;
    }

    public LimitRule getViolatedRule() {
        return violatedRule;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }

    public long getRemainingRequests() {
        return remainingRequests;
    }

    @Override
    public String toString() {
        if (allowed) {
            return "[ALLOWED] algo=" + algorithm + " remaining=" + remainingRequests + " reason=" + reason;
        }
        return "[DENIED] algo=" + algorithm + " rule=" + violatedRule
                + " retryAfter=" + retryAfterMillis + "ms reason=" + reason;
    }
}
