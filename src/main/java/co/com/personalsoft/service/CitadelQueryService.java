package co.com.personalsoft.service;

import co.com.personalsoft.domain.*; // for static metamodels
import co.com.personalsoft.domain.Citadel;
import co.com.personalsoft.repository.CitadelRepository;
import co.com.personalsoft.service.dto.CitadelCriteria;
import co.com.personalsoft.service.dto.CitadelDTO;
import co.com.personalsoft.service.mapper.CitadelMapper;
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
 * Service for executing complex queries for {@link Citadel} entities in the database.
 * The main input is a {@link CitadelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitadelDTO} or a {@link Page} of {@link CitadelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitadelQueryService extends QueryService<Citadel> {

    private final Logger log = LoggerFactory.getLogger(CitadelQueryService.class);

    private final CitadelRepository citadelRepository;

    private final CitadelMapper citadelMapper;

    public CitadelQueryService(CitadelRepository citadelRepository, CitadelMapper citadelMapper) {
        this.citadelRepository = citadelRepository;
        this.citadelMapper = citadelMapper;
    }

    /**
     * Return a {@link List} of {@link CitadelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitadelDTO> findByCriteria(CitadelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Citadel> specification = createSpecification(criteria);
        return citadelMapper.toDto(citadelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CitadelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitadelDTO> findByCriteria(CitadelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Citadel> specification = createSpecification(criteria);
        return citadelRepository.findAll(specification, page).map(citadelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitadelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Citadel> specification = createSpecification(criteria);
        return citadelRepository.count(specification);
    }

    /**
     * Function to convert {@link CitadelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Citadel> createSpecification(CitadelCriteria criteria) {
        Specification<Citadel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Citadel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Citadel_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Citadel_.address));
            }
            if (criteria.getStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStart(), Citadel_.start));
            }
            if (criteria.getFinish() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinish(), Citadel_.finish));
            }
        }
        return specification;
    }
}
