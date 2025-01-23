package app.cleanup.service;

import app.cleanup.config.ExternalPropertyReader;
import app.cleanup.entity.aud.ModulesAuditTrail;
import app.cleanup.enums.ConfigurableParameter;
import app.cleanup.repository.app.LostDeviceMgmtRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CleanupService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    Cleanup cleanup;
    @Autowired
    LostDeviceMgmtRepository lostDeviceMgmtRepository;
    @Autowired
    ModuleAuditTrailService moduleAuditTrailService;
    @Autowired
    ExternalPropertyReader externalPropertyReader;

    //@Scheduled(fixedDelayString = "#{@cleanup.getInitStartValue() * 60000 }")
    public void initStartProcess() {
        long executionStartTime = cleanup.currentTime();
        logger.info("Execution Start Time =" + executionStartTime);
        ModulesAuditTrail save = moduleAuditTrailService.insertionIntoModulesAuditTrail(externalPropertyReader.getModuleName(), externalPropertyReader.getFeatureName());
        long id = save.getId();
        try {
            LocalDateTime currentTime = LocalDateTime.now().minusMinutes(cleanup.getInitStartValue());
            String formattedTime = currentTime.format(dateTimeFormatter());
            logger.info("Minutes {}", formattedTime);
            List<String> requestIdsByStatusAndCreatedTimeLessThan = lostDeviceMgmtRepository.findRequestIdsByStatusAndCreatedTimeLessThan("INIT_START", formattedTime);
            if (requestIdsByStatusAndCreatedTimeLessThan.size() > 0) {
                logger.info("Request ids are going to update for INIT_START status {}", requestIdsByStatusAndCreatedTimeLessThan);
                lostDeviceMgmtRepository.updateStatus("CANCEL", requestIdsByStatusAndCreatedTimeLessThan);
            } else {
                logger.info("No request id found for INIT_START status");
            }
            this.updateRow(id, executionStartTime, ConfigurableParameter.Code200.getStatusCode(), ConfigurableParameter.Code200.getValue(), "Process completed for " + externalPropertyReader.getFeatureName());
        } catch (Exception e) {
            this.updateRow(id, executionStartTime, ConfigurableParameter.Code500.getStatusCode(), ConfigurableParameter.Code500.getValue(), e.getMessage());
            e.printStackTrace();
        }
    }

    //  @Scheduled(fixedDelayString = "#{@cleanup.getInitValue()  * 86400000 }")
    public void initProcess() {
        long executionStartTime = cleanup.currentTime();
        logger.info("Execution Start Time =" + executionStartTime);
        ModulesAuditTrail save = moduleAuditTrailService.insertionIntoModulesAuditTrail(externalPropertyReader.getModuleName(), externalPropertyReader.getFeatureName());
        long id = save.getId();
        try {
            LocalDateTime currentTime = LocalDateTime.now().minusDays(cleanup.getInitValue());
            String formattedTime = currentTime.format(dateTimeFormatter());
            logger.info("Days {}", formattedTime);
            List<String> requestIdsByStatusAndCreatedTimeLessThan = lostDeviceMgmtRepository.findRequestIdsByStatusAndCreatedTimeLessThan("INIT", formattedTime);
            if (requestIdsByStatusAndCreatedTimeLessThan.size() > 0) {
                logger.info("Request ids are going to update for INIT status {}", requestIdsByStatusAndCreatedTimeLessThan);
                lostDeviceMgmtRepository.updateStatus("CANCEL", requestIdsByStatusAndCreatedTimeLessThan);
            } else {
                logger.info("No request id found for INIT status");
            }
            this.updateRow(id, executionStartTime, ConfigurableParameter.Code200.getStatusCode(), ConfigurableParameter.Code200.getValue(), "Process completed for " + externalPropertyReader.getFeatureName2());
        } catch (Exception e) {
            this.updateRow(id, executionStartTime, ConfigurableParameter.Code500.getStatusCode(), ConfigurableParameter.Code500.getValue(), e.getMessage());
            e.printStackTrace();
        }

    }

    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public void updateRow(long id, long executionStartTime, int statusCode, String status, String info) {
        long executionfinalTime = cleanup.executionfinalTime(executionStartTime);
        ModuleAuditTrailModel moduleAuditTrailModel = ModuleAuditTrailModel.builder().info(info).executionTime(String.valueOf(executionfinalTime)).statusCode(statusCode).status(status).id(id).build();
        moduleAuditTrailService.updateIntoModulesAuditTrail(moduleAuditTrailModel);
    }
}
