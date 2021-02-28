package co.com.personalsoft.web.rest;

import co.com.personalsoft.service.BuildOrderQueryService;
import co.com.personalsoft.service.BuildOrderService;
import co.com.personalsoft.service.dto.BuildOrderCriteria;
import co.com.personalsoft.service.dto.BuildOrderDTO;
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
 * REST controller for managing {@link co.com.personalsoft.domain.BuildOrder}.
 */
@RestController
@RequestMapping("/api")
public class BuildOrderResource {

    private final Logger log = LoggerFactory.getLogger(BuildOrderResource.class);

    private static final String ENTITY_NAME = "buildOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuildOrderService buildOrderService;

    private final BuildOrderQueryService buildOrderQueryService;

    public BuildOrderResource(BuildOrderService buildOrderService, BuildOrderQueryService buildOrderQueryService) {
        this.buildOrderService = buildOrderService;
        this.buildOrderQueryService = buildOrderQueryService;
    }

    /**
     * {@code POST  /build-orders} : Create a new buildOrder.
     *
     * @param buildOrderDTO the buildOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buildOrderDTO, or with status {@code 400 (Bad Request)} if the buildOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/build-orders")
    public ResponseEntity<BuildOrderDTO> createBuildOrder(@Valid @RequestBody BuildOrderDTO buildOrderDTO) throws URISyntaxException {
        log.debug("REST request to save BuildOrder : {}", buildOrderDTO);
        if (buildOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildOrderDTO result = buildOrderService.save(buildOrderDTO);
        return ResponseEntity
            .created(new URI("/api/build-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /build-orders} : Updates an existing buildOrder.
     *
     * @param buildOrderDTO the buildOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildOrderDTO,
     * or with status {@code 400 (Bad Request)} if the buildOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buildOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/build-orders")
    public ResponseEntity<BuildOrderDTO> updateBuildOrder(@Valid @RequestBody BuildOrderDTO buildOrderDTO) throws URISyntaxException {
        log.debug("REST request to update BuildOrder : {}", buildOrderDTO);
        if (buildOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuildOrderDTO result = buildOrderService.save(buildOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /build-orders} : Updates given fields of an existing buildOrder.
     *
     * @param buildOrderDTO the buildOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildOrderDTO,
     * or with status {@code 400 (Bad Request)} if the buildOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buildOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buildOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/build-orders", consumes = "application/merge-patch+json")
    public ResponseEntity<BuildOrderDTO> partialUpdateBuildOrder(@NotNull @RequestBody BuildOrderDTO buildOrderDTO)
        throws URISyntaxException {
        log.debug("REST request to update BuildOrder partially : {}", buildOrderDTO);
        if (buildOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<BuildOrderDTO> result = buildOrderService.partialUpdate(buildOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /build-orders} : get all the buildOrders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buildOrders in body.
     */
    @GetMapping("/build-orders")
    public ResponseEntity<List<BuildOrderDTO>> getAllBuildOrders(BuildOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BuildOrders by criteria: {}", criteria);
        Page<BuildOrderDTO> page = buildOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /build-orders/count} : count all the buildOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/build-orders/count")
    public ResponseEntity<Long> countBuildOrders(BuildOrderCriteria criteria) {
        log.debug("REST request to count BuildOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(buildOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /build-orders/:id} : get the "id" buildOrder.
     *
     * @param id the id of the buildOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buildOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/build-orders/{id}")
    public ResponseEntity<BuildOrderDTO> getBuildOrder(@PathVariable Long id) {
        log.debug("REST request to get BuildOrder : {}", id);
        Optional<BuildOrderDTO> buildOrderDTO = buildOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buildOrderDTO);
    }

    /**
     * {@code DELETE  /build-orders/:id} : delete the "id" buildOrder.
     *
     * @param id the id of the buildOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/build-orders/{id}")
    public ResponseEntity<Void> deleteBuildOrder(@PathVariable Long id) {
        log.debug("REST request to delete BuildOrder : {}", id);
        buildOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
