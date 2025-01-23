package app.cleanup.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ExternalPropertyReader {
    @Value("${serverName}")
    String serverName;

    @Value("${module.name}")
    String moduleName;

    @Value("${minutewise.feature.name}")
    String featureName;

    @Value("${daywise.feature.name}")
    String featureName2;

}
