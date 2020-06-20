package lightstep.gson;

import lightstep.gson.Operation;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

public class TestOperation {

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(Operation.class)
                .suppress(Warning.NULL_FIELDS)
                .verify();
    }
}
