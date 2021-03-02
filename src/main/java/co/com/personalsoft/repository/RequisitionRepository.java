package co.com.personalsoft.repository;

import co.com.personalsoft.domain.Requisition;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Requisition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitionRepository extends JpaRepository<Requisition, Long>, JpaSpecificationExecutor<Requisition> {

  Optional<Requisition> findByCoordinate(String coordinate);
}
