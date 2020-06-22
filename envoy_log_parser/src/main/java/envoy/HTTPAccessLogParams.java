package envoy;

import javax.annotation.concurrent.NotThreadSafe;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

/**
 * The entries in each log message are (in order):
 *
 * [%START_TIME%]
 * "%REQ(:METHOD)%
 * %REQ(X-ENVOY-ORIGINAL-PATH?:PATH)%
 * %PROTOCOL%"
 * %RESPONSE_CODE%
 * %RESPONSE_FLAGS%
 * %BYTES_RECEIVED%
 * %BYTES_SENT%
 * %DURATION%
 * %RESP(X-ENVOY-UPSTREAM-SERVICE-TIME)%
 * "%REQ(X-FORWARDED-FOR)%"
 * "%REQ(USER-AGENT)%"
 * "%REQ(X-REQUEST-ID)%"
 * "%REQ(:AUTHORITY)%"
 * "%UPSTREAM_HOST%"
 * "%REQ(CUSTOMER_ID)%
 */
@NotThreadSafe
public final class HTTPAccessLogParams {

    public static final DateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

    private Instant startInstant;
    private String requestMethod;
    private String path;
    private String protocol;
    private int responseCode;
    private String responseFlags;
    private int responseBodyBytes;
    private int requestBodyBytes;
    private Duration downstreamDuration;
    private Duration upstreamDuration;
    private String xForwardedFor;
    private String userAgent;
    private String requestId;
    private String authority;
    private String upstreamHost;
    private int upstreamPort;
    private String customerId;

    public void setStartInstant(Instant startInstant) {
        this.startInstant = startInstant;
    }

    Instant getStartInstant() {
        return startInstant;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    String getRequestMethod() {
        return requestMethod;
    }

    public void setPath(String path) {
        this.path = path;
    }

    String getPath() {
        return path;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    String getProtocol() {
        return protocol;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    int getResponseCode() {
        return responseCode;
    }

    public void setResponseFlags(String responseFlags) {
        this.responseFlags = responseFlags;
    }

    String getResponseFlags() {
        return responseFlags;
    }

    public void setResponseBodyBytes(int responseBodyBytes) {
        this.responseBodyBytes = responseBodyBytes;
    }

    int getResponseBodyBytes() {
        return responseBodyBytes;
    }

    public void setRequestBodyBytes(int requestBodyBytes) {
        this.requestBodyBytes = requestBodyBytes;
    }

    int getRequestBodyBytes() {
        return requestBodyBytes;
    }

    public void setDownstreamDuration(Duration downstreamDuration) {
        this.downstreamDuration = downstreamDuration;
    }

    Duration getDownstreamDuration() {
        return downstreamDuration;
    }

    public void setUpstreamDuration(Duration upstreamDuration) {
        this.upstreamDuration = upstreamDuration;
    }

    Duration getUpstreamDuration() {
        return upstreamDuration;
    }

    public void setXForwardedFor(String xForwardedFor) {
        this.xForwardedFor = xForwardedFor;
    }

    String getXForwardedFor() {
        return xForwardedFor;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    String getUserAgent() {
        return userAgent;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    String getRequestId() {
        return requestId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    String getAuthority() {
        return authority;
    }

    public void setUpstreamHost(String upstreamHost) {
        this.upstreamHost = upstreamHost;
    }

    String getUpstreamHost() {
        return upstreamHost;
    }

    public void setUpstreamPort(int upstreamPort) {
        this.upstreamPort = upstreamPort;
    }

    int getUpstreamPort() {
        return upstreamPort;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    String getCustomerId() {
        return customerId;
    }
}
