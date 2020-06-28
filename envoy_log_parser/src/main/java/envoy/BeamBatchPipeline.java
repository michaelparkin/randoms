package envoy;

import io.envoyproxy.envoy.data.accesslog.v2.HTTPAccessLogEntry;
import org.HdrHistogram.Histogram;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeamBatchPipeline {

    private final static Logger LOG = LoggerFactory.getLogger(BeamBatchPipeline.class);

    public static void main(String[] args) {

        BeamBatchPipelineOptions options = PipelineOptionsFactory.fromArgs(args)
                .withValidation()
                .as(BeamBatchPipelineOptions.class);

        Pipeline p = Pipeline.create(options);

        PCollection<String> logEntries = p.apply(TextIO.read()
                .from(options.getFilePath()));

        // This is not quite what we want for streaming - we need to get the customerId from the
        // message, not the parsed params, but am not sure where that data is in the proto
        PCollection<KV<String, HTTPAccessLogEntry>> customerHttpAccessLogEntries =
                logEntries.apply(ParDo.of(new HTTPAccessLogDoFn()));

        PCollection<KV<String, Iterable<HTTPAccessLogEntry>>> logs =
                customerHttpAccessLogEntries.apply(GroupByKey.create());

        PCollection<KV<String, Histogram>> histograms =
                logs.apply(ParDo.of(new HistogramsDoFn()));

        PipelineResult.State finalState = p.run().waitUntilFinish();

        LOG.info("Final state was {}", finalState);
    }
}
