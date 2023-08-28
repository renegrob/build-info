package io.github.renegrob;

import io.quarkus.info.OsInfo;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus.info.build")
public interface BuildInfoConfiguration {
    String buildNumber();
    String jdk();
    OsInfo os();
}
