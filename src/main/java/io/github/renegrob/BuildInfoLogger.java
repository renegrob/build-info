package io.github.renegrob;

import io.quarkus.info.BuildInfo;
import io.quarkus.info.GitInfo;
import io.quarkus.info.JavaInfo;
import io.quarkus.info.OsInfo;
import io.quarkus.runtime.Startup;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BuildInfoLogger {

    public static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH:mm:ss.SSS z");
    private final Logger logger;

    private final BuildInfo buildInfo;
    private final GitInfo gitInfo;
    private final BuildInfoConfiguration buildInfoConfig;

    @Inject
    public BuildInfoLogger(Logger logger, BuildInfo buildInfo, GitInfo gitInfo, BuildInfoConfiguration buildInfoConfig) {
        this.logger = logger;
        this.buildInfo = buildInfo;
        this.gitInfo = gitInfo;
        this.buildInfoConfig = buildInfoConfig;
    }

    @Startup
    public void logBuildInfo() {
        logger.info("{}:{} version {}, build-time: {}",
                buildInfo.group(), buildInfo.artifact(), buildInfo.version(),
                formatDate(buildInfo.time()));
        logger.info("Built on OS: {} version {} on {}", buildInfoConfig.os().name(), buildInfoConfig.os().version(), buildInfoConfig.os().architecture());
        logger.info("Built with: {}", buildInfoConfig.jdk());
        logger.info("Git Info: Commit {} on branch {} at {}", gitInfo.latestCommitId(), gitInfo.branch(), formatDate(gitInfo.commitTime()));
    }

    private String formatDate(OffsetDateTime time) {
        return DATE_PATTERN.format(time.atZoneSameInstant(ZoneId.systemDefault()));
    }
}
