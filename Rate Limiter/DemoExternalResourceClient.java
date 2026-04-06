public class DemoExternalResourceClient implements ExternalResourceClient {

    @Override
    public String callExternalResource(String requestId, RateLimitKey key) {
        return "[EXTERNAL] called for " + requestId + " (quota key: " + key + ")";
    }
}
