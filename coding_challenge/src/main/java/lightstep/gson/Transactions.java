package lightstep.gson;

import com.google.common.annotations.VisibleForTesting;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * A container for all the transactions seen in the log file.
 */
class Transactions implements Consumer<GsonLogRecord> {

    @VisibleForTesting
    static final Comparator<Transaction> transactionTimeComparator =
            (t1, t2) -> Long.compare(t2.elapsedMillis(), t1.elapsedMillis());

    // Map of <Transaction id, Transaction>
    private final Map<String, Transaction> transactionMap;

    public Transactions() {
        this.transactionMap = new HashMap<>();
    }

    @Override
    public void accept(GsonLogRecord logRecord) {
        logRecord.addTo(this);
    }

    public void addStartLogRecord(String transactionId, GsonLogRecord logRecord) {
        Transaction transaction = transactionMap.computeIfAbsent(transactionId, Transaction::new);
        transaction.addStartLogRecord(logRecord);
    }

    public void addEndLogRecord(String transactionId, GsonLogRecord logRecord) {
        Transaction transaction = transactionMap.computeIfAbsent(transactionId, Transaction::new);
        transaction.addEndLogRecord(logRecord);
    }

    public void printLongestTransactions() {
        TreeSet<Transaction> transactionSet = new TreeSet<>(transactionTimeComparator);
        transactionSet.addAll(transactionMap.values());
        Transaction longest = transactionSet.first();
        System.out.println("The longest transaction was " + longest);
    }
}
