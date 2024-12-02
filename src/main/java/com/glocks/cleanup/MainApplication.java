package com.glocks.cleanup;

import com.glocks.cleanup.service.CleanupService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.setRegisterShutdownHook(false);
        ApplicationContext context = app.run(args);
        CleanupService cleanupService = (CleanupService) context.getBean("cleanupService");
        cleanupService.initStartProcess();
        cleanupService.initProcess();
    }
}
