package envoy;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestHttpAccessLogTokenizer {

    private final static String LOG_ENTRY = "[2020-06-18T06:25:25.123Z]" +
            " \"POST /grpc/method HTTP/2\" 200 - 110 74053 103 101 \"-\"" +
            " \"grpc-java-netty/1.25.0\" \"67432a84-621b-4d54-ba6d-9e0313a96304\"" +
            " \"127.0.0.1\" \"10.0.0.1:80\" \"customerId\"";

    private HTTPAccessLogTokenizer accessLogTokenizer;

    @Before
    public void setUp() {
        accessLogTokenizer = new HTTPAccessLogTokenizer();
    }

    @Test
    public void testTokenizeLogEntry() {

        HTTPAccessLogParams accessLogParams = accessLogTokenizer.apply(LOG_ENTRY);

        Instant startInstant = accessLogParams.getStartInstant();
        assertThat(startInstant.getEpochSecond(),
                is(equalTo(1592486725L)));
        assertThat(startInstant.getNano(),
                is(equalTo(123000000)));
        assertThat(accessLogParams.getRequestMethod(),
                is(equalTo("POST")));
        assertThat(accessLogParams.getPath(),
                is(equalTo("/grpc/method")));
        assertThat(accessLogParams.getProtocol(),
                is(equalTo("HTTP2")));
        assertThat(accessLogParams.getResponseCode(),
                is(equalTo(200)));
        assertThat(accessLogParams.getResponseFlags(),
                is(equalTo("-")));
        assertThat(accessLogParams.getResponseBodyBytes(),
                is(equalTo(110)));
        assertThat(accessLogParams.getRequestBodyBytes(),
                is(equalTo(74053)));
        assertThat(accessLogParams.getDownstreamDuration(),
                is(equalTo(Duration.ofMillis(103))));
        assertThat(accessLogParams.getUpstreamDuration(),
                is(equalTo(Duration.ofMillis(101))));
        assertThat((accessLogParams.getXForwardedFor()),
                is(equalTo("-")));
        assertThat(accessLogParams.getUserAgent(),
                is(equalTo("grpc-java-netty/1.25.0")));
        assertThat(accessLogParams.getRequestId(),
                is(equalTo("67432a84-621b-4d54-ba6d-9e0313a96304")));
        assertThat(accessLogParams.getAuthority(),
                is(equalTo("127.0.0.1")));
        assertThat(accessLogParams.getUpstreamHost(),
                is(equalTo("10.0.0.1")));
        assertThat(accessLogParams.getUpstreamPort(),
                is(equalTo(80)));
        assertThat(accessLogParams.getCustomerId(),
                is(equalTo("customerId")));
    }
}
