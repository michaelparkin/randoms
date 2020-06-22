package envoy;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

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
        Instant startInstant = Instant.now();
        accessLogParams.setStartInstant(startInstant);
        assertThat(accessLogParams.getStartInstant(),
                is(equalTo(startInstant)));
    }

    @Test
    public void testSetAndGet() {


    }
}
