package envoy;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestParseLogFile {

    private String logFilePathName;
    private HTTPAccessLogTokenizer accessLogTokenizer;
    private HTTPAccessLogEntryFunction accessLogEntryFunction;

    @Before
    public void setUp() {
        this.logFilePathName = System.getProperty("user.dir") + "/src/test/resources/test.log";
        this.accessLogTokenizer = new HTTPAccessLogTokenizer();
        this.accessLogEntryFunction = new HTTPAccessLogEntryFunction();
    }

    @Test
    public void testParseLogFile() throws IOException {
        File file = new File(logFilePathName);

        List<String> logEntries = Files.readLines(file, Charsets.UTF_8);
        assertThat(logEntries.size(), is(equalTo(36)));

        Set<HTTPAccessLogEntry> accessLogs = logEntries.stream()
                .map(logEntry -> accessLogTokenizer.apply(logEntry))
                .map(accessLogParams -> accessLogEntryFunction.apply(accessLogParams))
                .collect(Collectors.toCollection(() -> new HashSet<>(36)));
        assertThat(accessLogs.size(), is(equalTo(36)));
    }
}
