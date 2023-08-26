package io.github.renegrob;

import io.quarkus.info.BuildInfo;
import io.quarkus.info.GitInfo;
import io.quarkus.info.JavaInfo;
import io.quarkus.info.OsInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.slf4j.helpers.MessageFormatter.basicArrayFormat;


@Path("/build-info")
public class BuildInfoResource {

    public static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("uuuu-MM-dd-HH:mm:ss.SSS z");
    private final Logger logger;
    private final BuildInfo buildInfo;
    private final OsInfo osInfo;
    private final JavaInfo javaInfo;
    private final GitInfo gitInfo;

    @Inject
    public BuildInfoResource(Logger logger, BuildInfo buildInfo, OsInfo osInfo, JavaInfo javaInfo, GitInfo gitInfo) {
        this.logger = logger;
        this.buildInfo = buildInfo;
        this.osInfo = osInfo;
        this.javaInfo = javaInfo;
        this.gitInfo = gitInfo;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String buildInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(formatLine("{}:{} version {}, build-time: {}",
                buildInfo.group(), buildInfo.artifact(), buildInfo.version(),
                formatDate(buildInfo.time())));
        sb.append(formatLine("Built on OS: {} version {} on {}", osInfo.name(), osInfo.version(), osInfo.architecture()));
        sb.append(formatLine("Built with java: {}", javaInfo.version()));
        sb.append(formatLine("Git Info: Commit {} on branch {} at {}", gitInfo.latestCommitId(), gitInfo.branch(), formatDate(gitInfo.commitTime())));
        return sb.toString();
    }

    private String formatLine(String message, Object... args) {
        return basicArrayFormat(message, args) + "\n";
    }

    private String formatDate(OffsetDateTime time) {
        return DATE_PATTERN.format(time.atZoneSameInstant(ZoneId.systemDefault()));
    }

}
