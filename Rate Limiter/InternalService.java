public class InternalService {

    private final String serviceName;
    private final RateLimiter rateLimiter;
    private final ExternalResourceClient externalClient;
    private final RateLimitPolicy policy;

    public InternalService(String serviceName, RateLimiter rateLimiter,
                           ExternalResourceClient externalClient, RateLimitPolicy policy) {
        this.serviceName = serviceName;
        this.rateLimiter = rateLimiter;
        this.externalClient = externalClient;
        this.policy = policy;
    }

    public String handleRequest(String requestId, boolean needsExternalCall, RateLimitKey key) {
        System.out.println("[" + serviceName + "] received " + requestId);

        runBusinessLogic();

        if (!needsExternalCall) {
            return "  -> No external call needed for " + requestId;
        }

        RateLimitDecision decision = rateLimiter.tryAcquire(key, policy);
        if (!decision.isAllowed()) {
            return "  -> DENIED for " + requestId
                    + " | rule: " + decision.getViolatedRule().getName()
                    + " | retry after: " + decision.getRetryAfterMillis() + "ms";
        }

        String response = externalClient.callExternalResource(requestId, key);
        return "  -> " + response + " | remaining quota: " + decision.getRemainingRequests();
    }

    private void runBusinessLogic() {
    }
}
