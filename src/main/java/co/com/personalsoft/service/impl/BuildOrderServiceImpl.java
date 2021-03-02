package co.com.personalsoft.service.impl;

import co.com.personalsoft.domain.BuildOrder;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.repository.BuildOrderRepository;
import co.com.personalsoft.service.BuildOrderService;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.mapper.BuildOrderMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuildOrder}.
 */
@Service
@Transactional
public class BuildOrderServiceImpl implements BuildOrderService {

    private final Logger log = LoggerFactory.getLogger(BuildOrderServiceImpl.class);

    private final BuildOrderRepository buildOrderRepository;

    private final BuildOrderMapper buildOrderMapper;

    public BuildOrderServiceImpl(BuildOrderRepository buildOrderRepository, BuildOrderMapper buildOrderMapper) {
        this.buildOrderRepository = buildOrderRepository;
        this.buildOrderMapper = buildOrderMapper;
    }

    @Override
    public BuildOrderDTO save(BuildOrderDTO buildOrderDTO) {
        log.debug("Request to save BuildOrder : {}", buildOrderDTO);
        BuildOrder buildOrder = buildOrderMapper.toEntity(buildOrderDTO);
        buildOrder = buildOrderRepository.save(buildOrder);
        return buildOrderMapper.toDto(buildOrder);
    }

    @Override
    public Optional<BuildOrderDTO> partialUpdate(BuildOrderDTO buildOrderDTO) {
        log.debug("Request to partially update BuildOrder : {}", buildOrderDTO);

        return buildOrderRepository
            .findById(buildOrderDTO.getId())
            .map(
                existingBuildOrder -> {
                    if (buildOrderDTO.getState() != null) {
                        existingBuildOrder.setState(buildOrderDTO.getState());
                    }

                    if (buildOrderDTO.getStart() != null) {
                        existingBuildOrder.setStart(buildOrderDTO.getStart());
                    }

                    if (buildOrderDTO.getFinish() != null) {
                        existingBuildOrder.setFinish(buildOrderDTO.getFinish());
                    }

                    return existingBuildOrder;
                }
            )
            .map(buildOrderRepository::save)
            .map(buildOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuildOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BuildOrders");
        return buildOrderRepository.findAll(pageable).map(buildOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuildOrderDTO> findOne(Long id) {
        log.debug("Request to get BuildOrder : {}", id);
        return buildOrderRepository.findById(id).map(buildOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuildOrder : {}", id);
        buildOrderRepository.deleteById(id);
    }

    @Override
    public List<BuildOrderDTO> findByStateAndStart(BuildOrderState state, LocalDate start) {
      log.debug("Request to get BuildOrder by state and start : {}, {}", state, start);
      List<BuildOrder> buildOrders = buildOrderRepository.findByStateAndStart(state, start);
      return buildOrderMapper.toDto(buildOrders);
    }

    @Override
    public List<BuildOrderDTO> findByStateAndFinish(BuildOrderState state, LocalDate finish) {
      log.debug("Request to get BuildOrder by state and finish : {}, {}", state, finish);
      List<BuildOrder> buildOrders = buildOrderRepository.findByStateAndFinish(state, finish);
      return buildOrderMapper.toDto(buildOrders);
    }
}
