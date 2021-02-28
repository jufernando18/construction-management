package co.com.personalsoft.service;

import co.com.personalsoft.service.dto.CitadelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.com.personalsoft.domain.Citadel}.
 */
public interface CitadelService {
    /**
     * Save a citadel.
     *
     * @param citadelDTO the entity to save.
     * @return the persisted entity.
     */
    CitadelDTO save(CitadelDTO citadelDTO);

    /**
     * Partially updates a citadel.
     *
     * @param citadelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CitadelDTO> partialUpdate(CitadelDTO citadelDTO);

    /**
     * Get all the citadels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitadelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" citadel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitadelDTO> findOne(Long id);

    /**
     * Delete the "id" citadel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
