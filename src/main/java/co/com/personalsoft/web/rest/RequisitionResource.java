package co.com.personalsoft.web.rest;

import co.com.personalsoft.service.RequisitionQueryService;
import co.com.personalsoft.service.RequisitionService;
import co.com.personalsoft.service.dto.RequisitionCriteria;
import co.com.personalsoft.service.dto.RequisitionDTO;
import co.com.personalsoft.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.com.personalsoft.domain.Requisition}.
 */
@RestController
@RequestMapping("/api")
public class RequisitionResource {

    private final Logger log = LoggerFactory.getLogger(RequisitionResource.class);

    private static final String ENTITY_NAME = "requisition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequisitionService requisitionService;

    private final RequisitionQueryService requisitionQueryService;

    public RequisitionResource(RequisitionService requisitionService, RequisitionQueryService requisitionQueryService) {
        this.requisitionService = requisitionService;
        this.requisitionQueryService = requisitionQueryService;
    }

    /**
     * {@code POST  /requisitions} : Create a new requisition.
     *
     * @param requisitionDTO the requisitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requisitionDTO, or with status {@code 400 (Bad Request)} if the requisition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requisitions")
    public ResponseEntity<RequisitionDTO> createRequisition(@Valid @RequestBody RequisitionDTO requisitionDTO) throws URISyntaxException {
        log.debug("REST request to save Requisition : {}", requisitionDTO);
        if (requisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitionDTO result = requisitionService.save(requisitionDTO);
        return ResponseEntity
            .created(new URI("/api/requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requisitions} : Updates an existing requisition.
     *
     * @param requisitionDTO the requisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requisitionDTO,
     * or with status {@code 400 (Bad Request)} if the requisitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requisitions")
    public ResponseEntity<RequisitionDTO> updateRequisition(@Valid @RequestBody RequisitionDTO requisitionDTO) throws URISyntaxException {
        log.debug("REST request to update Requisition : {}", requisitionDTO);
        if (requisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitionDTO result = requisitionService.save(requisitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requisitions} : Updates given fields of an existing requisition.
     *
     * @param requisitionDTO the requisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requisitionDTO,
     * or with status {@code 400 (Bad Request)} if the requisitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requisitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requisitions", consumes = "application/merge-patch+json")
    public ResponseEntity<RequisitionDTO> partialUpdateRequisition(@NotNull @RequestBody RequisitionDTO requisitionDTO)
        throws URISyntaxException {
        log.debug("REST request to update Requisition partially : {}", requisitionDTO);
        if (requisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<RequisitionDTO> result = requisitionService.partialUpdate(requisitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requisitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /requisitions} : get all the requisitions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requisitions in body.
     */
    @GetMapping("/requisitions")
    public ResponseEntity<List<RequisitionDTO>> getAllRequisitions(RequisitionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Requisitions by criteria: {}", criteria);
        Page<RequisitionDTO> page = requisitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requisitions/count} : count all the requisitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/requisitions/count")
    public ResponseEntity<Long> countRequisitions(RequisitionCriteria criteria) {
        log.debug("REST request to count Requisitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(requisitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /requisitions/:id} : get the "id" requisition.
     *
     * @param id the id of the requisitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requisitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requisitions/{id}")
    public ResponseEntity<RequisitionDTO> getRequisition(@PathVariable Long id) {
        log.debug("REST request to get Requisition : {}", id);
        Optional<RequisitionDTO> requisitionDTO = requisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisitionDTO);
    }

    /**
     * {@code DELETE  /requisitions/:id} : delete the "id" requisition.
     *
     * @param id the id of the requisitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requisitions/{id}")
    public ResponseEntity<Void> deleteRequisition(@PathVariable Long id) {
        log.debug("REST request to delete Requisition : {}", id);
        requisitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
