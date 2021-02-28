package co.com.personalsoft.web.rest;

import co.com.personalsoft.service.BuildTypeQueryService;
import co.com.personalsoft.service.BuildTypeService;
import co.com.personalsoft.service.dto.BuildTypeCriteria;
import co.com.personalsoft.service.dto.BuildTypeDTO;
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
 * REST controller for managing {@link co.com.personalsoft.domain.BuildType}.
 */
@RestController
@RequestMapping("/api")
public class BuildTypeResource {

    private final Logger log = LoggerFactory.getLogger(BuildTypeResource.class);

    private static final String ENTITY_NAME = "buildType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuildTypeService buildTypeService;

    private final BuildTypeQueryService buildTypeQueryService;

    public BuildTypeResource(BuildTypeService buildTypeService, BuildTypeQueryService buildTypeQueryService) {
        this.buildTypeService = buildTypeService;
        this.buildTypeQueryService = buildTypeQueryService;
    }

    /**
     * {@code POST  /build-types} : Create a new buildType.
     *
     * @param buildTypeDTO the buildTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buildTypeDTO, or with status {@code 400 (Bad Request)} if the buildType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/build-types")
    public ResponseEntity<BuildTypeDTO> createBuildType(@Valid @RequestBody BuildTypeDTO buildTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BuildType : {}", buildTypeDTO);
        if (buildTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildTypeDTO result = buildTypeService.save(buildTypeDTO);
        return ResponseEntity
            .created(new URI("/api/build-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /build-types} : Updates an existing buildType.
     *
     * @param buildTypeDTO the buildTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildTypeDTO,
     * or with status {@code 400 (Bad Request)} if the buildTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buildTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/build-types")
    public ResponseEntity<BuildTypeDTO> updateBuildType(@Valid @RequestBody BuildTypeDTO buildTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BuildType : {}", buildTypeDTO);
        if (buildTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuildTypeDTO result = buildTypeService.save(buildTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /build-types} : Updates given fields of an existing buildType.
     *
     * @param buildTypeDTO the buildTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildTypeDTO,
     * or with status {@code 400 (Bad Request)} if the buildTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buildTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buildTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/build-types", consumes = "application/merge-patch+json")
    public ResponseEntity<BuildTypeDTO> partialUpdateBuildType(@NotNull @RequestBody BuildTypeDTO buildTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BuildType partially : {}", buildTypeDTO);
        if (buildTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<BuildTypeDTO> result = buildTypeService.partialUpdate(buildTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /build-types} : get all the buildTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buildTypes in body.
     */
    @GetMapping("/build-types")
    public ResponseEntity<List<BuildTypeDTO>> getAllBuildTypes(BuildTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BuildTypes by criteria: {}", criteria);
        Page<BuildTypeDTO> page = buildTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /build-types/count} : count all the buildTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/build-types/count")
    public ResponseEntity<Long> countBuildTypes(BuildTypeCriteria criteria) {
        log.debug("REST request to count BuildTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(buildTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /build-types/:id} : get the "id" buildType.
     *
     * @param id the id of the buildTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buildTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/build-types/{id}")
    public ResponseEntity<BuildTypeDTO> getBuildType(@PathVariable Long id) {
        log.debug("REST request to get BuildType : {}", id);
        Optional<BuildTypeDTO> buildTypeDTO = buildTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buildTypeDTO);
    }

    /**
     * {@code DELETE  /build-types/:id} : delete the "id" buildType.
     *
     * @param id the id of the buildTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/build-types/{id}")
    public ResponseEntity<Void> deleteBuildType(@PathVariable Long id) {
        log.debug("REST request to delete BuildType : {}", id);
        buildTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
