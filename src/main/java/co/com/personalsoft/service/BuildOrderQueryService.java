package co.com.personalsoft.service;

import co.com.personalsoft.domain.*; // for static metamodels
import co.com.personalsoft.domain.BuildOrder;
import co.com.personalsoft.repository.BuildOrderRepository;
import co.com.personalsoft.service.dto.BuildOrderCriteria;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.mapper.BuildOrderMapper;
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
 * Service for executing complex queries for {@link BuildOrder} entities in the database.
 * The main input is a {@link BuildOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BuildOrderDTO} or a {@link Page} of {@link BuildOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuildOrderQueryService extends QueryService<BuildOrder> {

    private final Logger log = LoggerFactory.getLogger(BuildOrderQueryService.class);

    private final BuildOrderRepository buildOrderRepository;

    private final BuildOrderMapper buildOrderMapper;

    public BuildOrderQueryService(BuildOrderRepository buildOrderRepository, BuildOrderMapper buildOrderMapper) {
        this.buildOrderRepository = buildOrderRepository;
        this.buildOrderMapper = buildOrderMapper;
    }

    /**
     * Return a {@link List} of {@link BuildOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BuildOrderDTO> findByCriteria(BuildOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BuildOrder> specification = createSpecification(criteria);
        return buildOrderMapper.toDto(buildOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BuildOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BuildOrderDTO> findByCriteria(BuildOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BuildOrder> specification = createSpecification(criteria);
        return buildOrderRepository.findAll(specification, page).map(buildOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BuildOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BuildOrder> specification = createSpecification(criteria);
        return buildOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link BuildOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BuildOrder> createSpecification(BuildOrderCriteria criteria) {
        Specification<BuildOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BuildOrder_.id));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), BuildOrder_.state));
            }
            if (criteria.getStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStart(), BuildOrder_.start));
            }
            if (criteria.getFinish() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinish(), BuildOrder_.finish));
            }
            if (criteria.getRequisitionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRequisitionId(),
                            root -> root.join(BuildOrder_.requisition, JoinType.LEFT).get(Requisition_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
