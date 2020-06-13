package michaelparkin;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;

@SuppressWarnings("unused")
public class StringFormatBenchmark {

    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        String customerId = "customerId";
        String lookupFeatureName = "lookupFeatureName";
        String featureVal = "featureVal";
        String FRAUD_LABEL = "fraudLabel";
        int numFraudUsers = 12345;
        long listSize = 9876L;
    }

    @SuppressWarnings("unused")
    @Benchmark
    public String testStringFormat(State state) {
        return String.format("Unexpected user list for key, customer: "
                        + "%s, att: %s, val: %s, label: %s. Expected count=%s, List size=%s",
                state.customerId, state.lookupFeatureName, state.featureVal, state.FRAUD_LABEL,
                state.numFraudUsers, state.listSize);
    }

    @SuppressWarnings("all")
    @Benchmark
    public String testStringBuilder(State state) {
        return new StringBuilder()
                .append("Unexpected user list for key, customer: ").append(state.customerId)
                .append(", att: ").append(state.lookupFeatureName)
                .append(", val: ").append(state.featureVal)
                .append(", label: ").append(state.FRAUD_LABEL)
                .append(", expected count: ").append(state.numFraudUsers)
                .append(", list size: ").append(state.listSize)
                .toString();
    }

    @SuppressWarnings("unused")
    @Benchmark
    public String testStringConcatenation(State state) {
        return "Unexpected user list for key, customer: " + state.customerId +
                ", att: " + state.lookupFeatureName +
                ", val: " + state.featureVal +
                ", label: " + state.FRAUD_LABEL +
                ", expected count: " + state.numFraudUsers +
                ", list size: " + state.listSize;
    }
}
