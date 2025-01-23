package app.cleanup.repository.app;

import app.cleanup.entity.app.LostDeviceMgmt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface LostDeviceMgmtRepository extends JpaRepository<LostDeviceMgmt, Long> {

    @Modifying
    @Query("UPDATE LostDeviceMgmt x SET x.status =:status WHERE x.requestId IN (:requestId)")
    public int updateStatus(String status, List<String> requestId);


    @Query(value = "SELECT r.request_id FROM lost_device_mgmt r WHERE r.status = :status AND r.created_on < :currentTime",nativeQuery = true)
    List<String> findRequestIdsByStatusAndCreatedTimeLessThan(String status, String currentTime);

}
