package dev.hrrezaei.blackjack.report;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Csv implements ReportGenerator {

    private final boolean isEnabled;
    private final List<String> headers = new ArrayList<>();
    private final List<List<String>> rows = new ArrayList<>();

    public Csv(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public List<List<String>> getRows() {
        return Collections.unmodifiableList(rows);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void generateReport(Path path) {
        if (isEnabled) {
            path.toFile().getParentFile().mkdirs();
            CSVFormat csvFormat = CSVFormat.Builder.create()
                    .setHeader(headers.toArray(new String[0]))
                    .build();

            try (PrintWriter writer = new PrintWriter(path.toFile());
                 CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {

                for (List<String> row : rows) {
                    csvPrinter.printRecord(row);
                }

            } catch (IOException e) {
                throw new RuntimeException("Failed to create CSV file", e);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void addHeaders(Function<Object, String> toString, Object... headers) {
        Arrays.stream(headers)
                .map(toString)
                .forEach(this.headers::add);
    }

    @Override
    public void addRow(Function<Object, String> toString, Object... rowElements) {
        List<String> row = new ArrayList<>();
        Arrays.stream(rowElements)
                .map(toString)
                .forEach(row::add);
        rows.add(row);
    }

}