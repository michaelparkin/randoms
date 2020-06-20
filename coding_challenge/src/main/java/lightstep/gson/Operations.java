package lightstep.gson;

import com.google.common.annotations.VisibleForTesting;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * A container for all the operations seen in the log file.
 */
class Operations implements Consumer<GsonLogRecord> {

    @VisibleForTesting
    static final Comparator<Operation> errorComparator =
            (o1, o2) -> Integer.compare(o2.getNoErrorTransactions(), o1.getNoErrorTransactions());

    // Map of <Operation name, Operation>
    private final Map<String, Operation> operationMap;

    public Operations() {
        this.operationMap = new HashMap<>();
    }

    @Override
    public void accept(GsonLogRecord logRecord) {
        logRecord.addTo(this);
    }

    public void addErrorTransactionId(String operationName, String transactionId) {
        Operation operation = operationMap.computeIfAbsent(operationName, Operation::new);
        operation.addErrorTransactionId(transactionId);
    }

    public void addTransactionId(String operationName, String transactionId) {
        Operation operation = operationMap.computeIfAbsent(operationName, Operation::new);
        operation.addTransactionId(transactionId);
    }

    public void printOperationsWithLargestErrors() {
        TreeSet<Operation> operationSet = new TreeSet<>(errorComparator);
        operationSet.addAll(operationMap.values());
        Operation mostErrors = operationSet.first();
        System.out.println("The operation with the largest number of errors is " + mostErrors);
    }
}
