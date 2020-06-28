package envoy;

import com.google.protobuf.Duration;

import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.util.function.Function;

@ThreadSafe
class MillisFromDuration implements Function<Duration, Long>, Serializable {

    @Override
    public Long apply(Duration duration) {
        long seconds = duration.getSeconds();
        long nanos = duration.getNanos();

        return (seconds * 1_000) + java.time.Duration.ofNanos(nanos).toMillis();
    }
}
