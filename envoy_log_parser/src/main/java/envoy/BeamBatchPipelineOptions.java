package envoy;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.ValueProvider;

public interface BeamBatchPipelineOptions extends PipelineOptions {

    @Description("Path to log file")
    ValueProvider<String> getFilePath();

    @SuppressWarnings("unused")
    void setFilePath(ValueProvider<String> filePath);
}
