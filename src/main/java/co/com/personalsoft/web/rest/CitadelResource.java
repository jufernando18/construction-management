package co.com.personalsoft.web.rest;

import co.com.personalsoft.service.CitadelQueryService;
import co.com.personalsoft.service.CitadelService;
import co.com.personalsoft.service.dto.CitadelCriteria;
import co.com.personalsoft.service.dto.CitadelDTO;
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
 * REST controller for managing {@link co.com.personalsoft.domain.Citadel}.
 */
@RestController
@RequestMapping("/api")
public class CitadelResource {

    private final Logger log = LoggerFactory.getLogger(CitadelResource.class);

    private static final String ENTITY_NAME = "citadel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitadelService citadelService;

    private final CitadelQueryService citadelQueryService;

    public CitadelResource(CitadelService citadelService, CitadelQueryService citadelQueryService) {
        this.citadelService = citadelService;
        this.citadelQueryService = citadelQueryService;
    }

    /**
     * {@code POST  /citadels} : Create a new citadel.
     *
     * @param citadelDTO the citadelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citadelDTO, or with status {@code 400 (Bad Request)} if the citadel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citadels")
    public ResponseEntity<CitadelDTO> createCitadel(@Valid @RequestBody CitadelDTO citadelDTO) throws URISyntaxException {
        log.debug("REST request to save Citadel : {}", citadelDTO);
        if (citadelDTO.getId() != null) {
            throw new BadRequestAlertException("A new citadel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitadelDTO result = citadelService.save(citadelDTO);
        return ResponseEntity
            .created(new URI("/api/citadels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citadels} : Updates an existing citadel.
     *
     * @param citadelDTO the citadelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citadelDTO,
     * or with status {@code 400 (Bad Request)} if the citadelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citadelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citadels")
    public ResponseEntity<CitadelDTO> updateCitadel(@Valid @RequestBody CitadelDTO citadelDTO) throws URISyntaxException {
        log.debug("REST request to update Citadel : {}", citadelDTO);
        if (citadelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CitadelDTO result = citadelService.save(citadelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citadelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /citadels} : Updates given fields of an existing citadel.
     *
     * @param citadelDTO the citadelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citadelDTO,
     * or with status {@code 400 (Bad Request)} if the citadelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the citadelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the citadelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/citadels", consumes = "application/merge-patch+json")
    public ResponseEntity<CitadelDTO> partialUpdateCitadel(@NotNull @RequestBody CitadelDTO citadelDTO) throws URISyntaxException {
        log.debug("REST request to update Citadel partially : {}", citadelDTO);
        if (citadelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<CitadelDTO> result = citadelService.partialUpdate(citadelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citadelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /citadels} : get all the citadels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citadels in body.
     */
    @GetMapping("/citadels")
    public ResponseEntity<List<CitadelDTO>> getAllCitadels(CitadelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Citadels by criteria: {}", criteria);
        Page<CitadelDTO> page = citadelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citadels/count} : count all the citadels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/citadels/count")
    public ResponseEntity<Long> countCitadels(CitadelCriteria criteria) {
        log.debug("REST request to count Citadels by criteria: {}", criteria);
        return ResponseEntity.ok().body(citadelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /citadels/:id} : get the "id" citadel.
     *
     * @param id the id of the citadelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citadelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citadels/{id}")
    public ResponseEntity<CitadelDTO> getCitadel(@PathVariable Long id) {
        log.debug("REST request to get Citadel : {}", id);
        Optional<CitadelDTO> citadelDTO = citadelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citadelDTO);
    }

    /**
     * {@code DELETE  /citadels/:id} : delete the "id" citadel.
     *
     * @param id the id of the citadelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citadels/{id}")
    public ResponseEntity<Void> deleteCitadel(@PathVariable Long id) {
        log.debug("REST request to delete Citadel : {}", id);
        citadelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
