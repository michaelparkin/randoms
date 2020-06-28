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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

import static io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry.HTTPVersion;
import static io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry.newBuilder;

@ThreadSafe
class HTTPAccessLogEntryFunction implements Function<HTTPAccessLogParams, HTTPAccessLogEntry>, Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(HTTPAccessLogEntryFunction.class);

    @Override
    public HTTPAccessLogEntry apply(HTTPAccessLogParams accessLogParams) {
        Instant startInstant = accessLogParams.getStartInstant();
        String requestMethod = accessLogParams.getRequestMethod();
        String path = accessLogParams.getPath();
        String protocol = accessLogParams.getProtocol();
        int responseCode = accessLogParams.getResponseCode();
        String responseFlags = accessLogParams.getResponseFlags(); // TODO
        int requestBodyBytes = accessLogParams.getRequestBodyBytes();
        int responseBodyBytes = accessLogParams.getResponseBodyBytes();
        Duration downstreamDuration = accessLogParams.getDownstreamDuration();
        Duration upstreamDuration = accessLogParams.getUpstreamDuration();
        String xForwardedFor = accessLogParams.getXForwardedFor(); // TODO
        String userAgent = accessLogParams.getUserAgent();
        String requestId = accessLogParams.getRequestId();
        String authority = accessLogParams.getAuthority();
        String upstreamHost = accessLogParams.getUpstreamHost();
        int upstreamPort = accessLogParams.getUpstreamPort();
        String customerId = accessLogParams.getCustomerId(); // TODO

        return newBuilder()
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
                .setProtocolVersion(HTTPVersion.valueOf(protocol))
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
