package dev.hrrezaei.blackjack.configuration;

import dev.hrrezaei.blackjack.report.Csv;
import dev.hrrezaei.blackjack.report.ReportGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static dev.hrrezaei.blackjack.configuration.EnvConfig.getProperty;

@Configuration
public class ReportConfig {

    private final String directory;
    private final Path reportDirectoryPath;
    private final Boolean isEnabled;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ReportConfig(Environment env) {
        this.directory = getProperty(env, "reports.directory", String.class, "reports");
        this.reportDirectoryPath = getDatedReportDirectoryPath();
        this.reportDirectoryPath.toFile().mkdirs();
        this.isEnabled = getProperty(env, "reports.enabled", Boolean.class, false);
    }

    private Path getDatedReportDirectoryPath() {
        String folderName = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        return Paths.get(directory).resolve(folderName);
    }

    public String getDirectory() {
        return directory;
    }

    public Path getReportDirectoryPath() {
        return reportDirectoryPath;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    @Bean
    public ReportGenerator reportGenerator() {
        return new Csv(isEnabled);
    }

}
