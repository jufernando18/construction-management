package co.com.personalsoft.service.impl;

import co.com.personalsoft.domain.Material;
import co.com.personalsoft.repository.MaterialRepository;
import co.com.personalsoft.service.MaterialService;
import co.com.personalsoft.service.dto.MaterialDTO;
import co.com.personalsoft.service.mapper.MaterialMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Material}.
 */
@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;

    private final MaterialMapper materialMapper;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    @Override
    public MaterialDTO save(MaterialDTO materialDTO) {
        log.debug("Request to save Material : {}", materialDTO);
        Material material = materialMapper.toEntity(materialDTO);
        material = materialRepository.save(material);
        return materialMapper.toDto(material);
    }

    @Override
    public Optional<MaterialDTO> partialUpdate(MaterialDTO materialDTO) {
        log.debug("Request to partially update Material : {}", materialDTO);

        return materialRepository
            .findById(materialDTO.getId())
            .map(
                existingMaterial -> {
                    if (materialDTO.getName() != null) {
                        existingMaterial.setName(materialDTO.getName());
                    }

                    if (materialDTO.getAcronym() != null) {
                        existingMaterial.setAcronym(materialDTO.getAcronym());
                    }

                    if (materialDTO.getAmountAvailable() != null) {
                        existingMaterial.setAmountAvailable(materialDTO.getAmountAvailable());
                    }

                    return existingMaterial;
                }
            )
            .map(materialRepository::save)
            .map(materialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable).map(materialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialDTO> findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id).map(materialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
    }
}
