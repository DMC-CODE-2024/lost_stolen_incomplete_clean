package app.cleanup.repository.app;

import app.cleanup.entity.app.SysParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysParamRepository extends JpaRepository<SysParam, Integer>, JpaSpecificationExecutor<SysParam> {

    @Query("select s.value from SysParam s where s.tag= :tag")
    String getValueFromTag(@Param("tag") String tag);

}
