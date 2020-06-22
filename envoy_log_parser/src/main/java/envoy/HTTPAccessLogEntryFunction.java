package envoy;

import com.google.protobuf.Timestamp;
import com.google.protobuf.UInt32Value;
import io.envoyproxy.envoy.api.v2.core.Address;
import io.envoyproxy.envoy.api.v2.core.RequestMethod;
import io.envoyproxy.envoy.api.v2.core.SocketAddress;
import io.envoyproxy.envoy.data.accesslog.v2.AccessLogCommon;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPRequestProperties;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPResponseProperties;
import io.envoyproxy.envoy.data.accesslog.v2.ResponseFlags;

import javax.annotation.concurrent.ThreadSafe;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

@ThreadSafe
public class HTTPAccessLogEntryFunction
        implements Function<HTTPAccessLogParams, HTTPAccessLogEntry> {

    @Override
    public HTTPAccessLogEntry apply(HTTPAccessLogParams httpAccessLogParams) {
        Instant startInstant = httpAccessLogParams.getStartInstant();
        String requestMethod = httpAccessLogParams.getRequestMethod();
        String path = httpAccessLogParams.getPath();
        String protocol = httpAccessLogParams.getProtocol();
        int responseCode = httpAccessLogParams.getResponseCode();
        String responseFlags = httpAccessLogParams.getResponseFlags(); // TODO
        int requestBodyBytes = httpAccessLogParams.getRequestBodyBytes();
        int responseBodyBytes = httpAccessLogParams.getResponseBodyBytes();
        Duration downstreamDuration = httpAccessLogParams.getDownstreamDuration();
        Duration upstreamDuration = httpAccessLogParams.getUpstreamDuration();
        String xForwardedFor = httpAccessLogParams.getXForwardedFor(); // TODO
        String userAgent = httpAccessLogParams.getUserAgent();
        String requestId = httpAccessLogParams.getRequestId();
        String authority = httpAccessLogParams.getAuthority();
        String upstreamHost = httpAccessLogParams.getUpstreamHost();
        int upstreamPort = httpAccessLogParams.getUpstreamPort();
        String customerId = httpAccessLogParams.getCustomerId(); // TODO

        return HTTPAccessLogEntry.newBuilder()
                .setCommonProperties(AccessLogCommon.newBuilder()
                        .setResponseFlags(ResponseFlags.newBuilder()
                                .build())
                        .setStartTime(Timestamp.newBuilder()
                                .setSeconds(startInstant.getEpochSecond())
                                .setNanos(startInstant.getNano())
                                .build())
                        .setTimeToLastUpstreamRxByte(com.google.protobuf.Duration.newBuilder()
                                .setSeconds(downstreamDuration.getSeconds())
                                .setNanos(downstreamDuration.getNano())
                                .build())
                        .setTimeToLastUpstreamTxByte(com.google.protobuf.Duration.newBuilder()
                                .setSeconds(upstreamDuration.getSeconds())
                                .setNanos(upstreamDuration.getNano())
                                .build())
                        .setUpstreamRemoteAddress(Address.newBuilder()
                                .setSocketAddress(SocketAddress.newBuilder()
                                        .setAddress(upstreamHost)
                                        .setPortValue(upstreamPort)
                                        .build())
                                .build())
                        .build())
                .setProtocolVersion(HTTPAccessLogEntry.HTTPVersion.valueOf(protocol))
                .setRequest(HTTPRequestProperties.newBuilder()
                        .setAuthority(authority)
                        .setPath(path)
                        .setRequestBodyBytes(requestBodyBytes)
                        .setRequestId(requestId)
                        .setRequestMethod(RequestMethod.valueOf(requestMethod))
                        .setUserAgent(userAgent)
                        .build())
                .setResponse(HTTPResponseProperties.newBuilder()
                        .setResponseBodyBytes(responseBodyBytes)
                        .setResponseCode(UInt32Value.newBuilder()
                                .setValue(responseCode)
                                .build())
                        .build())
                .build();
    }
}
