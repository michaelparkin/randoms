package envoy;

import java.time.Duration;
import java.time.Instant;

public class TestConstants {

    final static String LOG_ENTRY = "[2020-06-18T06:25:25.123Z]" +
            " \"POST /grpc/method HTTP/2\" 200 - 110 74053 103 101 \"-\"" +
            " \"grpc-java-netty/1.25.0\" \"67432a84-621b-4d54-ba6d-9e0313a96304\"" +
            " \"127.0.0.1\" \"10.0.0.1:80\" \"12345-abcde\"";

    final static Instant START_INSTANT = Instant.ofEpochSecond(1592486725L).plusNanos(123000000);

    final static String REQUEST_METHOD = "POST";

    final static String PATH = "/grpc/method";

    final static String PROTOCOL = "HTTP2";

    final static int RESPONSE_CODE = 200;

    final static String RESPONSE_FLAGS = "-";

    final static int RESPONSE_BODY_BYTES = 110;

    final static int REQUEST_BODY_BYTES = 74053;

    final static Duration DOWNSTREAM_DURATION = Duration.ofMillis(103);

    final static Duration UPSTREAM_DURATION = Duration.ofMillis(101);

    final static String X_FORWARDED_FOR = "-";

    final static String USER_AGENT = "grpc-java-netty/1.25.0";

    final static String REQUEST_ID = "67432a84-621b-4d54-ba6d-9e0313a96304";

    final static String AUTHORITY = "127.0.0.1";

    final static String UPSTREAM_HOST = "10.0.0.1";

    final static int UPSTREAM_PORT = 80;

    final static String CUTOMER_ID = "12345-abcde";
}
