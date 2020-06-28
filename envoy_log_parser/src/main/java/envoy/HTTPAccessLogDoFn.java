package envoy;

import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HTTPAccessLogDoFn extends DoFn<String, KV<String, HTTPAccessLogEntry>> {

    private final static Logger LOG = LoggerFactory.getLogger(HTTPAccessLogDoFn.class);

    private final HTTPAccessLogTokenizer accessLogTokenizer;
    private final HTTPAccessLogEntryFunction accessLogEntryFunction;

    HTTPAccessLogDoFn() {
        this.accessLogTokenizer = new HTTPAccessLogTokenizer();
        this.accessLogEntryFunction = new HTTPAccessLogEntryFunction();
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        String logEntry = c.element();
        HTTPAccessLogParams accessLogParams = accessLogTokenizer.apply(logEntry);
        LOG.debug("Processing {}", accessLogParams);
        HTTPAccessLogEntry accessLogEntry = accessLogEntryFunction.apply(accessLogParams);
        c.output(KV.of(accessLogParams.getCustomerId(), accessLogEntry));
    }
}
