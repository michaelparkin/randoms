package envoy;

import com.google.protobuf.Duration;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static envoy.HTTPAccessLogParams.DATE_FORMAT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestMillisFromDuration {

    private MillisFromDuration millisFromDuration;

    @Before
    public void setUp() {
        millisFromDuration = new MillisFromDuration();
    }

    Duration durationFromMillis(long millis) {
        java.time.Duration javaDuration = java.time.Duration.ofMillis(millis);
        return Duration.newBuilder()
                .setSeconds(javaDuration.getSeconds())
                .setNanos(javaDuration.getNano())
                .build();
    }

    @Test
    public void test0() {
        long millis = 0L;
        Duration duration = durationFromMillis(millis);
        assertThat(millisFromDuration.apply(duration), is(equalTo(millis)));
    }

    @Test
    public void test1() {
        long millis = 1L;
        Duration duration = durationFromMillis(millis);
        assertThat(millisFromDuration.apply(duration), is(equalTo(millis)));
    }

    @Test
    public void test1Second() {
        long millis = 1_000L;
        Duration duration = durationFromMillis(millis);
        assertThat(millisFromDuration.apply(duration), is(equalTo(millis)));
    }

    @Test
    public void test100Seconds() {
        long millis = 100_000L;
        Duration duration = durationFromMillis(millis);
        assertThat(millisFromDuration.apply(duration), is(equalTo(millis)));
    }

    @Test
    public void testFromDateString0() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        long millis = dateFormat.parse("1969-12-31T16:00:00.000Z")
                .toInstant()
                .toEpochMilli();
        Duration duration = durationFromMillis(millis);

        assertThat(millisFromDuration.apply(duration), is(equalTo(0L)));
    }

    @Test
    public void testFromDateString1() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        long millis = dateFormat.parse("2000-01-01T00:00:00.000Z")
                .toInstant()
                .toEpochMilli();
        Duration duration = durationFromMillis(millis);

        assertThat(millisFromDuration.apply(duration), is(equalTo(946713600000L)));
    }

    @Test
    public void testFromDateString2() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        long millis = dateFormat.parse("2020-06-18T06:25:25.062Z")
                .toInstant()
                .toEpochMilli();
        Duration duration = durationFromMillis(millis);

        assertThat(millisFromDuration.apply(duration), is(equalTo(1592486725062L)));
    }
}
