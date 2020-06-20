package lightstep.gson;

import lightstep.gson.Transaction;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

public class TestTransaction {

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(Transaction.class)
                .suppress(Warning.NULL_FIELDS)
                .withOnlyTheseFields("transactionId")
                .verify();
    }
}
