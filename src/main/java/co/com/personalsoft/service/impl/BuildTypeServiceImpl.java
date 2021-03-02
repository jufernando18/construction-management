package co.com.personalsoft.service.impl;

import co.com.personalsoft.domain.BuildType;
import co.com.personalsoft.repository.BuildTypeRepository;
import co.com.personalsoft.service.BuildTypeService;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.mapper.BuildTypeMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuildType}.
 */
@Service
@Transactional
public class BuildTypeServiceImpl implements BuildTypeService {

    private final Logger log = LoggerFactory.getLogger(BuildTypeServiceImpl.class);

    private final BuildTypeRepository buildTypeRepository;

    private final BuildTypeMapper buildTypeMapper;

    public BuildTypeServiceImpl(BuildTypeRepository buildTypeRepository, BuildTypeMapper buildTypeMapper) {
        this.buildTypeRepository = buildTypeRepository;
        this.buildTypeMapper = buildTypeMapper;
    }

    @Override
    public BuildTypeDTO save(BuildTypeDTO buildTypeDTO) {
        log.debug("Request to save BuildType : {}", buildTypeDTO);
        BuildType buildType = buildTypeMapper.toEntity(buildTypeDTO);
        buildType = buildTypeRepository.save(buildType);
        return buildTypeMapper.toDto(buildType);
    }

    @Override
    public Optional<BuildTypeDTO> partialUpdate(BuildTypeDTO buildTypeDTO) {
        log.debug("Request to partially update BuildType : {}", buildTypeDTO);

        return buildTypeRepository
            .findById(buildTypeDTO.getId())
            .map(
                existingBuildType -> {
                    if (buildTypeDTO.getName() != null) {
                        existingBuildType.setName(buildTypeDTO.getName());
                    }

                    if (buildTypeDTO.getDuration() != null) {
                        existingBuildType.setDuration(buildTypeDTO.getDuration());
                    }

                    if (buildTypeDTO.getAmountMaterial1() != null) {
                        existingBuildType.setAmountMaterial1(buildTypeDTO.getAmountMaterial1());
                    }

                    if (buildTypeDTO.getAmountMaterial2() != null) {
                        existingBuildType.setAmountMaterial2(buildTypeDTO.getAmountMaterial2());
                    }

                    if (buildTypeDTO.getAmountMaterial3() != null) {
                        existingBuildType.setAmountMaterial3(buildTypeDTO.getAmountMaterial3());
                    }

                    if (buildTypeDTO.getAmountMaterial4() != null) {
                        existingBuildType.setAmountMaterial4(buildTypeDTO.getAmountMaterial4());
                    }

                    if (buildTypeDTO.getAmountMaterial5() != null) {
                        existingBuildType.setAmountMaterial5(buildTypeDTO.getAmountMaterial5());
                    }

                    return existingBuildType;
                }
            )
            .map(buildTypeRepository::save)
            .map(buildTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuildTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BuildTypes");
        return buildTypeRepository.findAll(pageable).map(buildTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuildTypeDTO> findOne(Long id) {
        log.debug("Request to get BuildType : {}", id);
        return buildTypeRepository.findById(id).map(buildTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuildType : {}", id);
        buildTypeRepository.deleteById(id);
    }

    @Override
    public List<BuildTypeDTO> findAll() {
      log.debug("Request to get all BuildTypes");
      List<BuildType> buildTypes = buildTypeRepository.findAll();
      return buildTypeMapper.toDto(buildTypes);
    }
}
