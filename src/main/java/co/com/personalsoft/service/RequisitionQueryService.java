package co.com.personalsoft.service;

import co.com.personalsoft.domain.*; // for static metamodels
import co.com.personalsoft.domain.Requisition;
import co.com.personalsoft.repository.RequisitionRepository;
import co.com.personalsoft.service.dto.RequisitionCriteria;
import co.com.personalsoft.service.dto.RequisitionDTO;
import co.com.personalsoft.service.mapper.RequisitionMapper;
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
 * Service for executing complex queries for {@link Requisition} entities in the database.
 * The main input is a {@link RequisitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequisitionDTO} or a {@link Page} of {@link RequisitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequisitionQueryService extends QueryService<Requisition> {

    private final Logger log = LoggerFactory.getLogger(RequisitionQueryService.class);

    private final RequisitionRepository requisitionRepository;

    private final RequisitionMapper requisitionMapper;

    public RequisitionQueryService(RequisitionRepository requisitionRepository, RequisitionMapper requisitionMapper) {
        this.requisitionRepository = requisitionRepository;
        this.requisitionMapper = requisitionMapper;
    }

    /**
     * Return a {@link List} of {@link RequisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequisitionDTO> findByCriteria(RequisitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionMapper.toDto(requisitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RequisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequisitionDTO> findByCriteria(RequisitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionRepository.findAll(specification, page).map(requisitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequisitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionRepository.count(specification);
    }

    /**
     * Function to convert {@link RequisitionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Requisition> createSpecification(RequisitionCriteria criteria) {
        Specification<Requisition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Requisition_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Requisition_.name));
            }
            if (criteria.getCoordinate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoordinate(), Requisition_.coordinate));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), Requisition_.state));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Requisition_.date));
            }
            if (criteria.getBuildTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBuildTypeId(),
                            root -> root.join(Requisition_.buildType, JoinType.LEFT).get(BuildType_.id)
                        )
                    );
            }
            if (criteria.getCitadelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitadelId(), root -> root.join(Requisition_.citadel, JoinType.LEFT).get(Citadel_.id))
                    );
            }
        }
        return specification;
    }
}
