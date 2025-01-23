package app.cleanup.repository.aud;

import app.cleanup.entity.aud.ModulesAuditTrail;
import app.cleanup.service.ModuleAuditTrailModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface ModulesAuditTrailRepository extends JpaRepository<ModulesAuditTrail, Long> {
    @Modifying
    @Query("UPDATE ModulesAuditTrail m SET m.modifiedOn =CURRENT_TIMESTAMP,m.executionTime =:#{#mat.executionTime},m.statusCode =:#{#mat.statusCode},m.status =:#{#mat.status},m.info =:#{#mat.info} WHERE m.id =:#{#mat.id}")
    public int updateRecordBasedOnId(@Param("mat") ModuleAuditTrailModel moduleAuditTrailModel);

}
