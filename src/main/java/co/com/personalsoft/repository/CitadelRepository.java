package co.com.personalsoft.repository;

import co.com.personalsoft.domain.Citadel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Citadel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitadelRepository extends JpaRepository<Citadel, Long>, JpaSpecificationExecutor<Citadel> {}
