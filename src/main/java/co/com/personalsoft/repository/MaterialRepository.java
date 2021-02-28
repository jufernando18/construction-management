package co.com.personalsoft.repository;

import co.com.personalsoft.domain.Material;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Material entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {}
