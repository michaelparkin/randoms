package lightstep.gson;

import com.google.gson.annotations.SerializedName;
import lightstep.Level;

import javax.annotation.concurrent.Immutable;
import java.util.Date;

/**
 * Representation of a log record. Records are compared/sorted by timestamp.
 */
@Immutable
final class GsonLogRecord implements Comparable<GsonLogRecord> {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    protected static final int BEFORE = -1;
    protected static final int EQUAL = 0;
    protected static final int AFTER = 1;

    private final String service;
    private final Level level;
    private final Date timestamp;
    private final String operation;
    private final String message;
    @SerializedName("transaction_id")
    private final String transactionId;

    protected GsonLogRecord(String service, Level level, Date timestamp, String operation,
                            String message, String transactionId) {
        this.service = service;
        this.level = level;
        this.timestamp = timestamp;
        this.operation = operation;
        this.message = message;
        this.transactionId = transactionId;
    }

    void addTo(Transactions transactions) {
        if (message.startsWith("START")) {
            transactions.addStartLogRecord(transactionId, this);
        }
        if (message.startsWith("END")) {
            transactions.addEndLogRecord(transactionId, this);
        }
    }

    void addTo(Operations operations) {
        String operationName = message.split(" ", 2)[1];
        if (Level.ERROR.equals(level)) {
            operations.addErrorTransactionId(operationName, transactionId);
        } else {
            operations.addTransactionId(operationName, transactionId);
        }
    }

    long getEpochMillis() {
        return timestamp.toInstant().toEpochMilli();
    }

    long getMillsFrom(GsonLogRecord other) {
        return this.getEpochMillis() - other.getEpochMillis();
    }

    @Override
    public int compareTo(GsonLogRecord that) {
        if (this.timestamp.before(that.timestamp)) return BEFORE;
        if (this.timestamp.after(that.timestamp)) return AFTER;
        return EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GsonLogRecord logRecord = (GsonLogRecord) o;

        if (!service.equals(logRecord.service)) return false;
        if (level != logRecord.level) return false;
        if (!timestamp.equals(logRecord.timestamp)) return false;
        if (!operation.equals(logRecord.operation)) return false;
        if (!message.equals(logRecord.message)) return false;
        return transactionId.equals(logRecord.transactionId);
    }

    @Override
    public int hashCode() {
        int result = service.hashCode();
        result = 31 * result + level.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + operation.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + transactionId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogRecord{");
        sb.append("service='").append(service).append('\'');
        sb.append(", level=").append(level);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", operation='").append(operation).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", transactionId='").append(transactionId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
