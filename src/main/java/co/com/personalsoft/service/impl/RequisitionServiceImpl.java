package co.com.personalsoft.service.impl;

import co.com.personalsoft.domain.Requisition;
import co.com.personalsoft.repository.RequisitionRepository;
import co.com.personalsoft.service.RequisitionService;
import co.com.personalsoft.service.dto.RequisitionDTO;
import co.com.personalsoft.service.mapper.RequisitionMapper;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Requisition}.
 */
@Service
@Transactional
public class RequisitionServiceImpl implements RequisitionService {

    private final Logger log = LoggerFactory.getLogger(RequisitionServiceImpl.class);

    private final RequisitionRepository requisitionRepository;

    private final RequisitionMapper requisitionMapper;

    public RequisitionServiceImpl(RequisitionRepository requisitionRepository, RequisitionMapper requisitionMapper) {
        this.requisitionRepository = requisitionRepository;
        this.requisitionMapper = requisitionMapper;
    }

    @Override
    public RequisitionDTO save(RequisitionDTO requisitionDTO) {
        log.debug("Request to save Requisition : {}", requisitionDTO);
        Requisition requisition = requisitionMapper.toEntity(requisitionDTO);
        requisition = requisitionRepository.save(requisition);
        return requisitionMapper.toDto(requisition);
    }

    @Override
    public Optional<RequisitionDTO> partialUpdate(RequisitionDTO requisitionDTO) {
        log.debug("Request to partially update Requisition : {}", requisitionDTO);

        return requisitionRepository
            .findById(requisitionDTO.getId())
            .map(
                existingRequisition -> {
                    if (requisitionDTO.getName() != null) {
                        existingRequisition.setName(requisitionDTO.getName());
                    }

                    if (requisitionDTO.getCoordinate() != null) {
                        existingRequisition.setCoordinate(requisitionDTO.getCoordinate());
                    }

                    if (requisitionDTO.getState() != null) {
                        existingRequisition.setState(requisitionDTO.getState());
                    }

                    if (requisitionDTO.getDate() != null) {
                        existingRequisition.setDate(requisitionDTO.getDate());
                    }

                    return existingRequisition;
                }
            )
            .map(requisitionRepository::save)
            .map(requisitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requisitions");
        return requisitionRepository.findAll(pageable).map(requisitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequisitionDTO> findOne(Long id) {
        log.debug("Request to get Requisition : {}", id);
        return requisitionRepository.findById(id).map(requisitionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Requisition : {}", id);
        requisitionRepository.deleteById(id);
    }

    @Override
    public Optional<RequisitionDTO> findByCoordinate(String coordinate) {
      log.debug("Request to get Requisition by coordinate : {}", coordinate);
      return requisitionRepository.findByCoordinate(coordinate).map(requisitionMapper::toDto);
    }
}
