package envoy;

import com.google.protobuf.Duration;
import io.envoyproxy.envoy.data.accesslog.v2.AccessLogCommon;
import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import org.HdrHistogram.Histogram;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HistogramsDoFn extends DoFn<KV<String, Iterable<HTTPAccessLogEntry>>, KV<String, Histogram>> {

    private final static Logger LOG = LoggerFactory.getLogger(HistogramsDoFn.class);

    private final long highestTrackableValue;
    private final int numberOfSignificantValueDigits;
    private final MillisFromDuration millisFromDuration;

    HistogramsDoFn() {
        this.highestTrackableValue = 30_000L;
        this.numberOfSignificantValueDigits = 0;
        this.millisFromDuration = new MillisFromDuration();
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<String, Iterable<HTTPAccessLogEntry>> element = c.element();
        String customerId = element.getKey();
        Iterable<HTTPAccessLogEntry> accessLogs = element.getValue();

        Histogram histogram = new Histogram(highestTrackableValue, numberOfSignificantValueDigits);
        accessLogs.forEach(httpAccessLogEntry -> {
            AccessLogCommon commonProperties = httpAccessLogEntry.getCommonProperties();
            Duration downstreamDuration = commonProperties.getTimeToLastUpstreamRxByte();
            long millis = millisFromDuration.apply(downstreamDuration);
            histogram.recordValue(millis);
        });
        c.output(KV.of(customerId, histogram));
    }
}
