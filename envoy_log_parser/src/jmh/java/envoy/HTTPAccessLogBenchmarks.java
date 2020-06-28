package envoy;

import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import static envoy.TestConstants.AUTHORITY;
import static envoy.TestConstants.CUTOMER_ID;
import static envoy.TestConstants.DOWNSTREAM_DURATION;
import static envoy.TestConstants.LOG_ENTRY;
import static envoy.TestConstants.PATH;
import static envoy.TestConstants.PROTOCOL;
import static envoy.TestConstants.REQUEST_BODY_BYTES;
import static envoy.TestConstants.REQUEST_ID;
import static envoy.TestConstants.REQUEST_METHOD;
import static envoy.TestConstants.RESPONSE_BODY_BYTES;
import static envoy.TestConstants.RESPONSE_CODE;
import static envoy.TestConstants.RESPONSE_FLAGS;
import static envoy.TestConstants.START_INSTANT;
import static envoy.TestConstants.UPSTREAM_DURATION;
import static envoy.TestConstants.UPSTREAM_HOST;
import static envoy.TestConstants.UPSTREAM_PORT;
import static envoy.TestConstants.USER_AGENT;
import static envoy.TestConstants.X_FORWARDED_FOR;

@SuppressWarnings("unused")
@State(Scope.Benchmark)
public class HTTPAccessLogBenchmarks {

    private final HTTPAccessLogEntryFunction accessLogEntryFunction =
            new HTTPAccessLogEntryFunction();

    private final HTTPAccessLogTokenizer accessLogTokenizer =
            new HTTPAccessLogTokenizer();

    @Benchmark
    public HTTPAccessLogEntry testBaseline() {
        HTTPAccessLogParams accessLogParams = new HTTPAccessLogParams();
        accessLogParams.setStartInstant(START_INSTANT);
        accessLogParams.setRequestMethod(REQUEST_METHOD);
        accessLogParams.setPath(PATH);
        accessLogParams.setProtocol(PROTOCOL);
        accessLogParams.setResponseCode(RESPONSE_CODE);
        accessLogParams.setResponseFlags(RESPONSE_FLAGS);
        accessLogParams.setResponseBodyBytes(RESPONSE_BODY_BYTES);
        accessLogParams.setRequestBodyBytes(REQUEST_BODY_BYTES);
        accessLogParams.setDownstreamDuration(DOWNSTREAM_DURATION);
        accessLogParams.setUpstreamDuration(UPSTREAM_DURATION);
        accessLogParams.setXForwardedFor(X_FORWARDED_FOR);
        accessLogParams.setUserAgent(USER_AGENT);
        accessLogParams.setRequestId(REQUEST_ID);
        accessLogParams.setAuthority(AUTHORITY);
        accessLogParams.setUpstreamHost(UPSTREAM_HOST);
        accessLogParams.setUpstreamPort(UPSTREAM_PORT);
        accessLogParams.setCustomerId(CUTOMER_ID);
        return accessLogEntryFunction.apply(accessLogParams);
    }

    @Benchmark
    public HTTPAccessLogEntry testAccessLogTokenizer() {
        HTTPAccessLogParams accessLogParams = accessLogTokenizer.apply(LOG_ENTRY);
        return accessLogEntryFunction.apply(accessLogParams);
    }
}
