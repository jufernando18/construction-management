package co.com.personalsoft.repository;

import co.com.personalsoft.domain.BuildOrder;
import co.com.personalsoft.domain.enumeration.BuildOrderState;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BuildOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildOrderRepository extends JpaRepository<BuildOrder, Long>, JpaSpecificationExecutor<BuildOrder> {

  List<BuildOrder> findByStateAndStart(BuildOrderState state, LocalDate start);

  List<BuildOrder> findByStateAndFinish(BuildOrderState state, LocalDate finish);
}
