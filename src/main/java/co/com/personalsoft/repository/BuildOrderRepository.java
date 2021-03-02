package co.com.personalsoft.repository;

import co.com.personalsoft.domain.BuildOrder;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.dto.BuildTypeDTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BuildOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildOrderRepository extends JpaRepository<BuildOrder, Long>, JpaSpecificationExecutor<BuildOrder> {

  List<BuildOrder> findByStateAndStart(BuildOrderState state, LocalDate start);

  List<BuildOrder> findByStateAndFinish(BuildOrderState state, LocalDate finish);

  List<BuildOrder> findByState(BuildOrderState state);
  
  @Query("select bo from BuildOrder bo left join fetch bo.requisition r left join fetch r.buildType bt where bo.state = :state and bt.id = :buildTypeId")
  List<BuildOrder> findByStateAndBuildTypeId(@Param("state")BuildOrderState state, @Param("buildTypeId") Long buildTypeId);
}
