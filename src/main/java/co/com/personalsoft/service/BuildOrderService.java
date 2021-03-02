package co.com.personalsoft.service;

import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.dto.BuildOrderDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.com.personalsoft.domain.BuildOrder}.
 */
public interface BuildOrderService {
    /**
     * Save a buildOrder.
     *
     * @param buildOrderDTO the entity to save.
     * @return the persisted entity.
     */
    BuildOrderDTO save(BuildOrderDTO buildOrderDTO);

    /**
     * Partially updates a buildOrder.
     *
     * @param buildOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuildOrderDTO> partialUpdate(BuildOrderDTO buildOrderDTO);

    /**
     * Get all the buildOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuildOrderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" buildOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuildOrderDTO> findOne(Long id);

    /**
     * Delete the "id" buildOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<BuildOrderDTO> findByStateAndStart(BuildOrderState state, LocalDate start);
    
    List<BuildOrderDTO> findByStateAndFinish(BuildOrderState state, LocalDate finish);
}
