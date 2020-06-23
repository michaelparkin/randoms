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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestHTTPAccessLogParams {

    private HTTPAccessLogParams accessLogParams;

    @Before
    public void setUp() {
        this.accessLogParams = new HTTPAccessLogParams();
    }

    @Test
    public void testSetAndGetStartInstant() {
        accessLogParams.setStartInstant(START_INSTANT);
        assertThat(accessLogParams.getStartInstant(), is(equalTo(START_INSTANT)));
    }

    @Test
    public void testSetAndGetRequestMethod() {
        accessLogParams.setRequestMethod(REQUEST_METHOD);
        assertThat(accessLogParams.getRequestMethod(), is(equalTo(REQUEST_METHOD)));
    }

    @Test
    public void testSetAndGetPath() {
        accessLogParams.setPath(PATH);
        assertThat(accessLogParams.getPath(), is(equalTo(PATH)));
    }

    @Test
    public void testSetAndGetProtocol() {
        accessLogParams.setProtocol(PROTOCOL);
        assertThat(accessLogParams.getProtocol(), is(equalTo(PROTOCOL)));
    }

    @Test
    public void testSetAndGetResponseCode() {
        accessLogParams.setResponseCode(RESPONSE_CODE);
        assertThat(accessLogParams.getResponseCode(), is(equalTo(RESPONSE_CODE)));
    }

    @Test
    public void testSetAndGetResponseFlags() {
        accessLogParams.setResponseFlags(RESPONSE_FLAGS);
        assertThat(accessLogParams.getResponseFlags(), is(equalTo(RESPONSE_FLAGS)));
    }

    @Test
    public void testSetAndGetResponseBodyBytes() {
        accessLogParams.setResponseBodyBytes(RESPONSE_BODY_BYTES);
        assertThat(accessLogParams.getResponseBodyBytes(), is(equalTo(RESPONSE_BODY_BYTES)));
    }

    @Test
    public void testSetAndGetRequestBodyBytes() {
        accessLogParams.setRequestBodyBytes(REQUEST_BODY_BYTES);
        assertThat(accessLogParams.getRequestBodyBytes(), is(equalTo(REQUEST_BODY_BYTES)));
    }

    @Test
    public void testSetAndGetDownstreamDuration() {
        accessLogParams.setDownstreamDuration(DOWNSTREAM_DURATION);
        assertThat(accessLogParams.getDownstreamDuration(), is(equalTo(DOWNSTREAM_DURATION)));
    }

    @Test
    public void testSetAndGetUpstreamDuration() {
        accessLogParams.setUpstreamDuration(UPSTREAM_DURATION);
        assertThat(accessLogParams.getUpstreamDuration(), is(equalTo(UPSTREAM_DURATION)));
    }

    @Test
    public void testSetAndGetXForwardedFor() {
        accessLogParams.setXForwardedFor(X_FORWARDED_FOR);
        assertThat(accessLogParams.getXForwardedFor(), is(equalTo(X_FORWARDED_FOR)));
    }

    @Test
    public void testSetAndGetUserAgent() {
        accessLogParams.setUserAgent(USER_AGENT);
        assertThat(accessLogParams.getUserAgent(), is(equalTo(USER_AGENT)));
    }

    @Test
    public void testSetAndGetRequestId() {
        accessLogParams.setRequestId(REQUEST_ID);
        assertThat(accessLogParams.getRequestId(), is(equalTo(REQUEST_ID)));
    }

    @Test
    public void testSetAndGetAuthority() {
        accessLogParams.setAuthority(AUTHORITY);
        assertThat(accessLogParams.getAuthority(), is(equalTo(AUTHORITY)));
    }

    @Test
    public void testSetAndGetUpstreamHost() {
        accessLogParams.setUpstreamHost(UPSTREAM_HOST);
        assertThat(accessLogParams.getUpstreamHost(), is(equalTo(UPSTREAM_HOST)));
    }

    @Test
    public void testSetAndGetUpstreamPort() {
        accessLogParams.setUpstreamPort(UPSTREAM_PORT);
        assertThat(accessLogParams.getUpstreamPort(), is(equalTo(UPSTREAM_PORT)));
    }

    @Test
    public void testSetAndGetCustomerId() {
        accessLogParams.setCustomerId(CUTOMER_ID);
        assertThat(accessLogParams.getCustomerId(), is(equalTo(CUTOMER_ID)));
    }
}
