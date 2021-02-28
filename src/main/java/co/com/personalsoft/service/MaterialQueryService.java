package co.com.personalsoft.service;

import co.com.personalsoft.domain.*; // for static metamodels
import co.com.personalsoft.domain.Material;
import co.com.personalsoft.repository.MaterialRepository;
import co.com.personalsoft.service.dto.MaterialCriteria;
import co.com.personalsoft.service.dto.MaterialDTO;
import co.com.personalsoft.service.mapper.MaterialMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Material} entities in the database.
 * The main input is a {@link MaterialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaterialDTO} or a {@link Page} of {@link MaterialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialQueryService extends QueryService<Material> {

    private final Logger log = LoggerFactory.getLogger(MaterialQueryService.class);

    private final MaterialRepository materialRepository;

    private final MaterialMapper materialMapper;

    public MaterialQueryService(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    /**
     * Return a {@link List} of {@link MaterialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaterialDTO> findByCriteria(MaterialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Material> specification = createSpecification(criteria);
        return materialMapper.toDto(materialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MaterialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialDTO> findByCriteria(MaterialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.findAll(specification, page).map(materialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaterialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.count(specification);
    }

    /**
     * Function to convert {@link MaterialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Material> createSpecification(MaterialCriteria criteria) {
        Specification<Material> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Material_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Material_.name));
            }
            if (criteria.getAcronym() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcronym(), Material_.acronym));
            }
            if (criteria.getAmountAvailable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountAvailable(), Material_.amountAvailable));
            }
        }
        return specification;
    }
}
