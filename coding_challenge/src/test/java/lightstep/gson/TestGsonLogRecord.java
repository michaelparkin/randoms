package lightstep.gson;

import lightstep.gson.GsonLogRecord;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

public class TestGsonLogRecord {

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(GsonLogRecord.class)
                .suppress(Warning.NULL_FIELDS)
                .verify();
    }
}
