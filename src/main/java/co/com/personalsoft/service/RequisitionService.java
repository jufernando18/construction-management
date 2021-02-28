package co.com.personalsoft.service;

import co.com.personalsoft.service.dto.RequisitionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.com.personalsoft.domain.Requisition}.
 */
public interface RequisitionService {
    /**
     * Save a requisition.
     *
     * @param requisitionDTO the entity to save.
     * @return the persisted entity.
     */
    RequisitionDTO save(RequisitionDTO requisitionDTO);

    /**
     * Partially updates a requisition.
     *
     * @param requisitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequisitionDTO> partialUpdate(RequisitionDTO requisitionDTO);

    /**
     * Get all the requisitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequisitionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" requisition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequisitionDTO> findOne(Long id);

    /**
     * Delete the "id" requisition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
