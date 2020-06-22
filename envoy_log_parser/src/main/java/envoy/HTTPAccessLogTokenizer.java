package envoy;

import com.google.common.net.HostAndPort;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.StringTokenizer;
import java.util.function.Function;

import static envoy.HTTPAccessLogParams.DATE_FORMAT;

public class HTTPAccessLogTokenizer implements Function<String, HTTPAccessLogParams> {

    @Override
    public HTTPAccessLogParams apply(String logEntry) {

        final StringTokenizer st = new StringTokenizer(logEntry);

        final HTTPAccessLogParams accessLogParams = new HTTPAccessLogParams();

        Instant startInstant;
        try {
            startInstant = DATE_FORMAT.parse(st.nextToken()
                    .replace("[", "")
                    .replace("]", "")).toInstant();
        } catch (ParseException e) {
            startInstant = Instant.now();
        }
        accessLogParams.setStartInstant(startInstant);

        accessLogParams.setRequestMethod(st.nextToken()
                .replace("\"", ""));

        accessLogParams.setPath(st.nextToken());

        accessLogParams.setProtocol(st.nextToken()
                .replace("\"", "")
                .replace("/", ""));

        accessLogParams.setResponseCode(Integer.parseInt(st.nextToken()));

        accessLogParams.setResponseFlags(st.nextToken());

        accessLogParams.setResponseBodyBytes(Integer.parseInt(st.nextToken()));

        accessLogParams.setRequestBodyBytes(Integer.parseInt(st.nextToken()));

        int downstreamMillis = Integer.parseInt(st.nextToken());
        Duration downstreamDuration = Duration.ofMillis(downstreamMillis);
        accessLogParams.setDownstreamDuration(downstreamDuration);

        int upstreamMillis = Integer.parseInt(st.nextToken());
        Duration upstreamDuration = Duration.ofMillis(upstreamMillis);
        accessLogParams.setUpstreamDuration(upstreamDuration);

        accessLogParams.setXForwardedFor(st.nextToken()
                .replace("\"", ""));

        accessLogParams.setUserAgent(st.nextToken()
                .replace("\"", ""));

        accessLogParams.setRequestId(st.nextToken()
                .replace("\"", ""));

        accessLogParams.setAuthority(st.nextToken()
                .replace("\"", ""));

        HostAndPort upstreamHostAndPort = HostAndPort.fromString(st.nextToken()
                .replace("\"", ""));
        accessLogParams.setUpstreamHost(upstreamHostAndPort.getHost());
        accessLogParams.setUpstreamPort(upstreamHostAndPort.getPort());

        if (st.hasMoreTokens()) {
            accessLogParams.setCustomerId(st.nextToken()
                .replace("\"", ""));
        }

        return accessLogParams;
    }
}
