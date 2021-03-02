package co.com.personalsoft.service;

import co.com.personalsoft.service.dto.BuildTypeDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.com.personalsoft.domain.BuildType}.
 */
public interface BuildTypeService {
    /**
     * Save a buildType.
     *
     * @param buildTypeDTO the entity to save.
     * @return the persisted entity.
     */
    BuildTypeDTO save(BuildTypeDTO buildTypeDTO);

    /**
     * Partially updates a buildType.
     *
     * @param buildTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuildTypeDTO> partialUpdate(BuildTypeDTO buildTypeDTO);

    /**
     * Get all the buildTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuildTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" buildType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuildTypeDTO> findOne(Long id);

    /**
     * Delete the "id" buildType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<BuildTypeDTO> findAll();
}
