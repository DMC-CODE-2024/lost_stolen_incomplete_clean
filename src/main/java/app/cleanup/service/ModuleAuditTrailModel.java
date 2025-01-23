package app.cleanup.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModuleAuditTrailModel {
    private long id;
    private String modifiedOn;
    private String executionTime;
    private int statusCode;
    private String status;
    private String info;
}
