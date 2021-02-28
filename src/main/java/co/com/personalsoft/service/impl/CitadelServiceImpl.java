package co.com.personalsoft.service.impl;

import co.com.personalsoft.domain.Citadel;
import co.com.personalsoft.repository.CitadelRepository;
import co.com.personalsoft.service.CitadelService;
import co.com.personalsoft.service.dto.CitadelDTO;
import co.com.personalsoft.service.mapper.CitadelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Citadel}.
 */
@Service
@Transactional
public class CitadelServiceImpl implements CitadelService {

    private final Logger log = LoggerFactory.getLogger(CitadelServiceImpl.class);

    private final CitadelRepository citadelRepository;

    private final CitadelMapper citadelMapper;

    public CitadelServiceImpl(CitadelRepository citadelRepository, CitadelMapper citadelMapper) {
        this.citadelRepository = citadelRepository;
        this.citadelMapper = citadelMapper;
    }

    @Override
    public CitadelDTO save(CitadelDTO citadelDTO) {
        log.debug("Request to save Citadel : {}", citadelDTO);
        Citadel citadel = citadelMapper.toEntity(citadelDTO);
        citadel = citadelRepository.save(citadel);
        return citadelMapper.toDto(citadel);
    }

    @Override
    public Optional<CitadelDTO> partialUpdate(CitadelDTO citadelDTO) {
        log.debug("Request to partially update Citadel : {}", citadelDTO);

        return citadelRepository
            .findById(citadelDTO.getId())
            .map(
                existingCitadel -> {
                    if (citadelDTO.getName() != null) {
                        existingCitadel.setName(citadelDTO.getName());
                    }

                    if (citadelDTO.getAddress() != null) {
                        existingCitadel.setAddress(citadelDTO.getAddress());
                    }

                    if (citadelDTO.getStart() != null) {
                        existingCitadel.setStart(citadelDTO.getStart());
                    }

                    if (citadelDTO.getFinish() != null) {
                        existingCitadel.setFinish(citadelDTO.getFinish());
                    }

                    return existingCitadel;
                }
            )
            .map(citadelRepository::save)
            .map(citadelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CitadelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Citadels");
        return citadelRepository.findAll(pageable).map(citadelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitadelDTO> findOne(Long id) {
        log.debug("Request to get Citadel : {}", id);
        return citadelRepository.findById(id).map(citadelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Citadel : {}", id);
        citadelRepository.deleteById(id);
    }
}
