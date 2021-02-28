package co.com.personalsoft.repository;

import co.com.personalsoft.domain.BuildOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BuildOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildOrderRepository extends JpaRepository<BuildOrder, Long>, JpaSpecificationExecutor<BuildOrder> {}
