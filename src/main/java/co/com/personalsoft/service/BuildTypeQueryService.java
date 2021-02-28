package co.com.personalsoft.service;

import co.com.personalsoft.domain.*; // for static metamodels
import co.com.personalsoft.domain.BuildType;
import co.com.personalsoft.repository.BuildTypeRepository;
import co.com.personalsoft.service.dto.BuildTypeCriteria;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.mapper.BuildTypeMapper;
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
 * Service for executing complex queries for {@link BuildType} entities in the database.
 * The main input is a {@link BuildTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BuildTypeDTO} or a {@link Page} of {@link BuildTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuildTypeQueryService extends QueryService<BuildType> {

    private final Logger log = LoggerFactory.getLogger(BuildTypeQueryService.class);

    private final BuildTypeRepository buildTypeRepository;

    private final BuildTypeMapper buildTypeMapper;

    public BuildTypeQueryService(BuildTypeRepository buildTypeRepository, BuildTypeMapper buildTypeMapper) {
        this.buildTypeRepository = buildTypeRepository;
        this.buildTypeMapper = buildTypeMapper;
    }

    /**
     * Return a {@link List} of {@link BuildTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BuildTypeDTO> findByCriteria(BuildTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BuildType> specification = createSpecification(criteria);
        return buildTypeMapper.toDto(buildTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BuildTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BuildTypeDTO> findByCriteria(BuildTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BuildType> specification = createSpecification(criteria);
        return buildTypeRepository.findAll(specification, page).map(buildTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BuildTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BuildType> specification = createSpecification(criteria);
        return buildTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link BuildTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BuildType> createSpecification(BuildTypeCriteria criteria) {
        Specification<BuildType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BuildType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BuildType_.name));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), BuildType_.duration));
            }
            if (criteria.getAmountMaterial1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountMaterial1(), BuildType_.amountMaterial1));
            }
            if (criteria.getAmountMaterial2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountMaterial2(), BuildType_.amountMaterial2));
            }
            if (criteria.getAmountMaterial3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountMaterial3(), BuildType_.amountMaterial3));
            }
            if (criteria.getAmountMaterial4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountMaterial4(), BuildType_.amountMaterial4));
            }
            if (criteria.getAmountMaterial5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountMaterial5(), BuildType_.amountMaterial5));
            }
            if (criteria.getMaterial1Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterial1Id(),
                            root -> root.join(BuildType_.material1, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
            if (criteria.getMaterial2Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterial2Id(),
                            root -> root.join(BuildType_.material2, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
            if (criteria.getMaterial3Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterial3Id(),
                            root -> root.join(BuildType_.material3, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
            if (criteria.getMaterial4Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterial4Id(),
                            root -> root.join(BuildType_.material4, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
            if (criteria.getMaterial5Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterial5Id(),
                            root -> root.join(BuildType_.material5, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
