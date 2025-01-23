package app.cleanup.service;

import app.cleanup.repository.app.SysParamRepository;
import app.cleanup.repository.aud.ModulesAuditTrailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Predicate;


@Component
public class Cleanup {

    private final Logger logger = LogManager.getLogger(Cleanup.class);
    private final SysParamRepository sysParamRepository;

    public Cleanup(SysParamRepository sysParamRepository) {
        this.sysParamRepository = sysParamRepository;
    }


    public long getInitStartValue() {
        String initStartStatusOtpDuration = sysParamRepository.getValueFromTag("INIT_START_STATUS_OTP_DURATION");

        if (predicate.test(initStartStatusOtpDuration)) {
            return Long.parseLong(initStartStatusOtpDuration);
        }
        logger.info("Incorrect value {} of INIT_START_STATUS_OTP_DURATION ", initStartStatusOtpDuration);
        logger.info("default value is 10 min,in case any invalid value found");
        return 10L;
    }

    public long getInitValue() {
        String initStatusOtpDuration = sysParamRepository.getValueFromTag("INIT_STATUS_OTP_DURATION");
        if (predicate.test(initStatusOtpDuration)) {
            return Long.parseLong(initStatusOtpDuration);
        }
        logger.info("Incorrect value {} of INIT_STATUS_OTP_DURATION ", initStatusOtpDuration);
        logger.info("default value is 30 days,in case any invalid value found");
        return 30L;
    }

    public static boolean isPositiveInteger(String value) {
        return value != null && value.matches("^\\d+$");
    }

    Predicate<String> predicate = Cleanup::isPositiveInteger;

    public long currentTime() {
        Date date = new Date();
        long executionStartTime = date.getTime();
        logger.info("Execution Start Time =" + executionStartTime);
        return executionStartTime;
    }

    public long executionfinalTime(long executionStartTime) {
        long executionfinalTime = this.currentTime() - executionStartTime;
        logger.info("Final Execution Finish Time" + executionfinalTime);
        return executionfinalTime;
    }


}
