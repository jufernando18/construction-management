package co.com.personalsoft.repository;

import co.com.personalsoft.domain.BuildType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BuildType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildTypeRepository extends JpaRepository<BuildType, Long>, JpaSpecificationExecutor<BuildType> {}
