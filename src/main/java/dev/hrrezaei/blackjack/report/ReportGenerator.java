package dev.hrrezaei.blackjack.report;

import java.nio.file.Path;
import java.util.function.Function;

public interface ReportGenerator {

    void generateReport(Path path);

    boolean isEnabled();

    void addHeaders(Function<Object, String> toString, Object... headers);

    default void addHeaders(Object... headers) {
        Function<Object, String> toString = String::valueOf;
        addHeaders(toString, headers);
    }

    void addRow(Function<Object, String> toString, Object... rowElements);

    default void addRow(Object... rowElements) {
        Function<Object, String> toString = String::valueOf;
        addHeaders(toString, rowElements);
    }

}
