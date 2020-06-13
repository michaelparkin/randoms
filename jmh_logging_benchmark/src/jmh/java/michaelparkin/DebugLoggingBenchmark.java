package michaelparkin;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class DebugLoggingBenchmark {

    private static final Logger logger = LoggerFactory.getLogger(DebugLoggingBenchmark.class);

    @State(Scope.Thread)
    public static class MyState {
        String w = "test1";
        String x = "test2";
        String y = "test3";
        String z = "test4";
    }

    @SuppressWarnings("unused")
    @Benchmark
    public void testDebugConcatenatingStrings(MyState state) {
        logger.debug("Concatenating strings "
                + state.w + state.x + ", " + state.y + ", " + state.z);
    }

    @SuppressWarnings("unused")
    @Benchmark
    public void testDebugStringFormat(MyState state) {
        logger.debug(String.format("String format %s, %s, %s, %s",
                state.w, state.x, state.y, state.z));
    }

    @SuppressWarnings("all")
    @Benchmark
    public void testDebugObjectArrayArguments(MyState state) {
        logger.debug("Object array arguments {}, {}, {}, {}",
                new Object[]{ state.w, state.x, state.y, state.z });
    }

    @SuppressWarnings("unused")
    @Benchmark
    public void testDebugVariableArguments(MyState state) {
        logger.debug("Variable arguments {}, {}, {}, {}",
                state.x, state.x, state.y, state.z);
    }

    @SuppressWarnings("all")
    @Benchmark
    public void testDebugIfDebugEnabled(MyState state) {
        if (logger.isDebugEnabled()) {
            logger.debug("If debug enabled {}, {}, {}, {}",
                    state.w, state.x, state.y, state.z);
        }
    }
}
