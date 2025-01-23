package app.cleanup.service;

import app.cleanup.config.ExternalPropertyReader;
import app.cleanup.entity.aud.ModulesAuditTrail;
import app.cleanup.enums.ConfigurableParameter;
import app.cleanup.repository.aud.ModulesAuditTrailRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ModuleAuditTrailService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ModulesAuditTrailRepository modulesAuditTrailRepository;
    @Autowired
    private ExternalPropertyReader externalPropertyReader;


    public <T extends ModulesAuditTrail> ModulesAuditTrail insertionIntoModulesAuditTrail(String module, String feature) {
        ModulesAuditTrail modulesAuditTrail = ModulesAuditTrail.builder()
                .moduleName(module)
                .featureName(feature)
                .statusCode(ConfigurableParameter.Code201.getStatusCode())
                .status(ConfigurableParameter.Code201.getValue())
                .serverName(externalPropertyReader.getServerName())
                .executionTime("0")
                .build();
        logger.info("modulesAuditTrail saved for module in {} and feature {} with payload {}", module, feature, modulesAuditTrail);
        return modulesAuditTrailRepository.save(modulesAuditTrail);
    }

    public <T extends ModulesAuditTrail> void updateIntoModulesAuditTrail(ModuleAuditTrailModel modulesAuditTrailModel) {
        logger.info("modulesAuditTrail updated for row {} with payload {}", modulesAuditTrailModel.getId(), modulesAuditTrailModel);
        modulesAuditTrailRepository.updateRecordBasedOnId(modulesAuditTrailModel);
    }
}
