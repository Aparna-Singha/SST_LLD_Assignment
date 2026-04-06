public interface ExternalResourceClient {
    String callExternalResource(String requestId, RateLimitKey key);
}
