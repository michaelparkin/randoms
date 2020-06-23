package envoy;

import com.google.protobuf.Duration;
import com.google.protobuf.Timestamp;
import com.google.protobuf.UInt32Value;
import io.envoyproxy.envoy.api.v2.core.Address;
import io.envoyproxy.envoy.api.v2.core.RequestMethod;
import io.envoyproxy.envoy.api.v2.core.SocketAddress;
import io.envoyproxy.envoy.data.accesslog.v2.AccessLogCommon;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPRequestProperties;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPResponseProperties;
import org.junit.Before;
import org.junit.Test;

import static envoy.TestConstants.AUTHORITY;
import static envoy.TestConstants.CUTOMER_ID;
import static envoy.TestConstants.DOWNSTREAM_DURATION;
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
import static io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry.HTTPVersion;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestHTTPAccessLogFunction {

    private HTTPAccessLogParams accessLogParams;
    private HTTPAccessLogEntryFunction accessLogEntryFunction;

    @Before
    public void setUp() {
        accessLogParams = mock(HTTPAccessLogParams.class);
        accessLogEntryFunction = new HTTPAccessLogEntryFunction();
    }

    @Test
    public void test() {
        expect(accessLogParams.getStartInstant()).andReturn(START_INSTANT).once();
        expect(accessLogParams.getRequestMethod()).andReturn(REQUEST_METHOD).once();
        expect(accessLogParams.getPath()).andReturn(PATH).once();
        expect(accessLogParams.getProtocol()).andReturn(PROTOCOL).once();
        expect(accessLogParams.getResponseCode()).andReturn(RESPONSE_CODE).once();
        expect(accessLogParams.getResponseFlags()).andReturn(RESPONSE_FLAGS).once();
        expect(accessLogParams.getRequestBodyBytes()).andReturn(REQUEST_BODY_BYTES).once();
        expect(accessLogParams.getResponseBodyBytes()).andReturn(RESPONSE_BODY_BYTES).once();
        expect(accessLogParams.getDownstreamDuration()).andReturn(DOWNSTREAM_DURATION).once();
        expect(accessLogParams.getUpstreamDuration()).andReturn(UPSTREAM_DURATION).once();
        expect(accessLogParams.getXForwardedFor()).andReturn(X_FORWARDED_FOR).once();
        expect(accessLogParams.getUserAgent()).andReturn(USER_AGENT).once();
        expect(accessLogParams.getRequestId()).andReturn(REQUEST_ID).once();
        expect(accessLogParams.getAuthority()).andReturn(AUTHORITY).once();
        expect(accessLogParams.getUpstreamHost()).andReturn(UPSTREAM_HOST).once();
        expect(accessLogParams.getUpstreamPort()).andReturn(UPSTREAM_PORT).once();
        expect(accessLogParams.getCustomerId()).andReturn(CUTOMER_ID).once();

        replay(accessLogParams);

        HTTPAccessLogEntry accessLogEntry = accessLogEntryFunction.apply(accessLogParams);

        verify(accessLogParams);

        AccessLogCommon commonProperties = accessLogEntry.getCommonProperties();

        Timestamp startTime = commonProperties.getStartTime();
        assertThat(startTime.getSeconds(), is(equalTo(START_INSTANT.getEpochSecond())));
        assertThat(startTime.getNanos(), is(equalTo(START_INSTANT.getNano())));

        Duration downstreamDuration = commonProperties.getTimeToLastUpstreamRxByte();
        assertThat(downstreamDuration.getSeconds(), is(equalTo(DOWNSTREAM_DURATION.getSeconds())));
        assertThat(downstreamDuration.getNanos(), is(equalTo(DOWNSTREAM_DURATION.getNano())));

        Duration upstreamDuration = commonProperties.getTimeToLastUpstreamTxByte();
        assertThat(upstreamDuration.getSeconds(), is(equalTo(UPSTREAM_DURATION.getSeconds())));
        assertThat(upstreamDuration.getNanos(), is(equalTo(UPSTREAM_DURATION.getNano())));

        Address address = commonProperties.getUpstreamRemoteAddress();
        SocketAddress socketAddress = address.getSocketAddress();
        assertThat(socketAddress.getAddress(), is(equalTo(UPSTREAM_HOST)));
        assertThat(socketAddress.getPortValue(), is(equalTo(UPSTREAM_PORT)));

        assertThat(accessLogEntry.getProtocolVersion(), is(equalTo(HTTPVersion.HTTP2)));

        HTTPRequestProperties requestProperties = accessLogEntry.getRequest();

        assertThat(requestProperties.getAuthority(), is(equalTo(AUTHORITY)));
        assertThat(requestProperties.getPath(), is(equalTo(PATH)));
        assertThat(requestProperties.getRequestBodyBytes(), is(equalTo((long) REQUEST_BODY_BYTES)));
        assertThat(requestProperties.getRequestId(), is(equalTo(REQUEST_ID)));
        assertThat(requestProperties.getRequestMethod(), is(equalTo(RequestMethod.valueOf(REQUEST_METHOD))));
        assertThat(requestProperties.getUserAgent(), is(equalTo(USER_AGENT)));

        HTTPResponseProperties responseProperties = accessLogEntry.getResponse();

        assertThat(responseProperties.getResponseBodyBytes(), is(equalTo((long) RESPONSE_BODY_BYTES)));
        assertThat(responseProperties.getResponseCode(), is(equalTo(UInt32Value.of(RESPONSE_CODE))));
    }
}
