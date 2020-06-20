package lightstep.gson;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

class GsonFileParser {

    private final String fileName;

    public GsonFileParser(String fileName) {
        this.fileName = fileName;
    }

    public void consumeLogRecords(Consumer<GsonLogRecord> consumer) throws IOException {
        FileInputStream fin = new FileInputStream(fileName);
        BufferedInputStream bin = new BufferedInputStream(fin);
        consumeLogRecords(bin, consumer);
    }

    private void consumeLogRecords(InputStream in, Consumer<GsonLogRecord> consumer)
            throws IOException {
        Gson gson = new GsonBuilder().setDateFormat(GsonLogRecord.DATE_FORMAT).create();
        InputStreamReader inReader = new InputStreamReader(in, StandardCharsets.UTF_8);
        JsonReader reader = new JsonReader(inReader);
        reader.beginArray();
        while (reader.hasNext()) {
            GsonLogRecord logRecord = gson.fromJson(reader, GsonLogRecord.class);
            consumer.accept(logRecord);
        }
        reader.endArray();
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        Preconditions.checkArgument(args.length == 1,
                "Please supply the input file location");

        String fileName = args[0];

        System.out.println("Using " + fileName + " as input records file");

        Operations operations = new Operations();
        Transactions transactions = new Transactions();

        Consumer<GsonLogRecord> consumer = operations.andThen(transactions);
        new GsonFileParser(fileName).consumeLogRecords(consumer);

        operations.printOperationsWithLargestErrors();
        transactions.printLongestTransactions();
    }
}
