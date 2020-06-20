package lightstep.gson;

import java.util.HashSet;
import java.util.Set;

/**
 * An {@code Operation} is a specific piece of work completed by a service and is composed of
 * multiple {@link Transaction}s
 */
final class Operation {

    private final String name;
    private final Set<String> errorTransactions;
    private final Set<String> transactions;

    Operation(String name) {
        this.name = name;
        this.errorTransactions = new HashSet<>();
        this.transactions = new HashSet<>();
    }

    void addErrorTransactionId(String transactionId) {
        transactions.remove(transactionId);
        errorTransactions.add(transactionId);
    }

    void addTransactionId(String transactionId) {
        if (!errorTransactions.contains(transactionId)) {
            transactions.add(transactionId);
        }
    }

    int getNoTransactions() {
        return errorTransactions.size() + transactions.size();
    }

    int getNoErrorTransactions() {
        return errorTransactions.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (!name.equals(operation.name)) return false;
        if (!errorTransactions.equals(operation.errorTransactions)) return false;
        return transactions.equals(operation.transactions);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + errorTransactions.hashCode();
        return 31 * result + transactions.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Operation{");
        sb.append("name='").append(name).append('\'');
        sb.append(", errorTransactions=").append(errorTransactions.size());
        sb.append('}');
        return sb.toString();
    }
}
