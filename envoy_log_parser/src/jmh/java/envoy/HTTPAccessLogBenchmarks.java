package envoy;

import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.text.ParseException;
import java.time.Duration;

import static envoy.HTTPAccessLogParams.DATE_FORMAT;

@SuppressWarnings("unused")
@State(Scope.Benchmark)
public class HTTPAccessLogBenchmarks {

    public static final String LOG_ENTRY = "[2020-06-18T06:25:25.013Z] \"POST " +
            "/grpc/method HTTP/2\" 200 - 484 27587 67 66 \"-\" " +
            "\"grpc-java-netty/1.25.0\" \"e357c968-80f2-4dfa-b404-5189333a6c88\" " +
            "\"127.0.0.1\" \"10.0.0.1:80\" \"customerId\"";

    private final HTTPAccessLogEntryFunction accessLogEntryFunction =
            new HTTPAccessLogEntryFunction();

    private final HTTPAccessLogParams accessLogParams =
            new HTTPAccessLogParams();

    private final HTTPAccessLogTokenizer accessLogTokenizer =
            new HTTPAccessLogTokenizer();

    @Benchmark
    public HTTPAccessLogEntry testBaseline() throws ParseException {
        accessLogParams.setStartInstant(
                DATE_FORMAT.parse("2020-06-18T06:25:25.013Z").toInstant());
        accessLogParams.setRequestMethod("POST");
        accessLogParams.setPath("/grpc/method");
        accessLogParams.setProtocol("HTTP2");
        accessLogParams.setResponseCode(200);
        accessLogParams.setResponseFlags("-");
        accessLogParams.setResponseBodyBytes(484);
        accessLogParams.setRequestBodyBytes(27587);
        accessLogParams.setDownstreamDuration(Duration.ofMillis(67));
        accessLogParams.setUpstreamDuration(Duration.ofMillis(66));
        accessLogParams.setXForwardedFor("-");
        accessLogParams.setUserAgent("grpc-java-netty/1.25.0");
        accessLogParams.setRequestId("e357c968-80f2-4dfa-b404-5189333a6c88");
        accessLogParams.setAuthority("127.0.0.1");
        accessLogParams.setUpstreamHost("10.0.0.1");
        accessLogParams.setUpstreamPort(80);
        accessLogParams.setCustomerId("customerId");
        return accessLogEntryFunction.apply(accessLogParams);
    }

    @Benchmark
    public HTTPAccessLogEntry testAccessLogTokenizer() {
        HTTPAccessLogParams params = accessLogTokenizer.apply(LOG_ENTRY);
        return accessLogEntryFunction.apply(params);
    }
}
