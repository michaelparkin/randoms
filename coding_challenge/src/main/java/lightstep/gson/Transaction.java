package lightstep.gson;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * A {@link Transaction} represents a single action that is taken that might involve multiple
 * services and operations. Each transaction has a unique id (transaction_id) and may compose of
 * multiple start and end record.
 * <p/>
 * The time to complete a transaction is defined as the "duration between the timestamp of the
 * START of the first operation and the timestamp of the END of the last operation".
 */
final class Transaction {

    private final String transactionId;
    private final PriorityQueue<GsonLogRecord> startLogRecords;
    private final PriorityQueue<GsonLogRecord> endLogRecords;

    Transaction(String transactionId) {
        this.transactionId = transactionId;
        this.startLogRecords = new PriorityQueue<>();
        this.endLogRecords = new PriorityQueue<>(Collections.reverseOrder());
    }

    void addStartLogRecord(GsonLogRecord logRecord) {
        startLogRecords.add(logRecord);
    }

    void addEndLogRecord(GsonLogRecord logRecord) {
        endLogRecords.add(logRecord);
    }

    int noLogRecords() {
        return startLogRecords.size() + endLogRecords.size();
    }

    long elapsedMillis() {
        GsonLogRecord first = startLogRecords.peek();
        GsonLogRecord last = endLogRecords.peek();
        if (last != null && first != null) {
            return last.getMillsFrom(first);
        } else {
            return Long.MAX_VALUE;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return transactionId.equals(that.transactionId);
    }

    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("transactionId='").append(transactionId).append('\'');
        sb.append(", elapsedMillis=").append(elapsedMillis());
        sb.append('}');
        return sb.toString();
    }
}
