package com.glocks.cleanup.service;

import com.glocks.cleanup.repository.app.LostDeviceMgmtRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    //@Scheduled(fixedDelayString = "#{@cleanup.getInitStartValue() * 60000 }")
    public void initStartProcess() {
        LocalDateTime currentTime = LocalDateTime.now().minusMinutes(cleanup.getInitStartValue());
        String formattedTime = currentTime.format(dateTimeFormatter());
        logger.info("Minutes {}", formattedTime);
        List<String> requestIdsByStatusAndCreatedTimeLessThan = lostDeviceMgmtRepository.findRequestIdsByStatusAndCreatedTimeLessThan("INIT_START", formattedTime);
        if (requestIdsByStatusAndCreatedTimeLessThan.size() > 0) {
            logger.info("Request ids are going to update for INIT_START status {}", requestIdsByStatusAndCreatedTimeLessThan);
            lostDeviceMgmtRepository.updateStatus("CANCEL", requestIdsByStatusAndCreatedTimeLessThan);
        } else logger.info("No request id found for INIT_START status");
    }

    //@Scheduled(fixedDelayString = "#{@cleanup.getInitValue()  * 86400000 }")
    public void initProcess() {
        LocalDateTime currentTime = LocalDateTime.now().minusDays(cleanup.getInitValue());
        String formattedTime = currentTime.format(dateTimeFormatter());
        logger.info("Days {}", formattedTime);
        List<String> requestIdsByStatusAndCreatedTimeLessThan = lostDeviceMgmtRepository.findRequestIdsByStatusAndCreatedTimeLessThan("INIT", formattedTime);
        if (requestIdsByStatusAndCreatedTimeLessThan.size() > 0) {
            logger.info("Request ids are going to update for INIT status {}", requestIdsByStatusAndCreatedTimeLessThan);
            lostDeviceMgmtRepository.updateStatus("CANCEL", requestIdsByStatusAndCreatedTimeLessThan);
        } else logger.info("No request id found for INIT status");

    }

    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
}
