package envoy;

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
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class TestHttpAccessLogTokenizer {

    private HTTPAccessLogParams accessLogParams;
    private HTTPAccessLogTokenizer accessLogTokenizer;

    @Before
    public void setUp() {
        accessLogParams = mock(HTTPAccessLogParams.class);
        accessLogTokenizer = new HTTPAccessLogTokenizer();
    }

    @Test
    public void testTokenizeLogEntry() {
        accessLogParams.setStartInstant(START_INSTANT);
        expectLastCall().once();

        accessLogParams.setRequestMethod(REQUEST_METHOD);
        expectLastCall().once();

        accessLogParams.setPath(PATH);
        expectLastCall().once();

        accessLogParams.setProtocol(PROTOCOL);
        expectLastCall().once();

        accessLogParams.setResponseCode(RESPONSE_CODE);
        expectLastCall().once();

        accessLogParams.setResponseFlags(RESPONSE_FLAGS);
        expectLastCall().once();

        accessLogParams.setResponseBodyBytes(RESPONSE_BODY_BYTES);
        expectLastCall().once();

        accessLogParams.setRequestBodyBytes(REQUEST_BODY_BYTES);
        expectLastCall().once();

        accessLogParams.setDownstreamDuration(DOWNSTREAM_DURATION);
        expectLastCall().once();

        accessLogParams.setUpstreamDuration(UPSTREAM_DURATION);
        expectLastCall().once();

        accessLogParams.setXForwardedFor(X_FORWARDED_FOR);
        expectLastCall().once();

        accessLogParams.setUserAgent(USER_AGENT);
        expectLastCall().once();

        accessLogParams.setRequestId(REQUEST_ID);
        expectLastCall().once();

        accessLogParams.setAuthority(AUTHORITY);
        expectLastCall().once();

        accessLogParams.setUpstreamHost(UPSTREAM_HOST);
        expectLastCall().once();

        accessLogParams.setUpstreamPort(UPSTREAM_PORT);
        expectLastCall().once();

        accessLogParams.setCustomerId(CUTOMER_ID);
        expectLastCall().once();

        replay(accessLogParams);

        accessLogTokenizer.apply(accessLogParams, TestConstants.LOG_ENTRY);

        verify(accessLogParams);
    }
}
