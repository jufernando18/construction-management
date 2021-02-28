package co.com.personalsoft.service.mapper;

import co.com.personalsoft.domain.*;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuildType} and its DTO {@link BuildTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { MaterialMapper.class })
public interface BuildTypeMapper extends EntityMapper<BuildTypeDTO, BuildType> {
    @Mapping(target = "material1", source = "material1", qualifiedByName = "name")
    @Mapping(target = "material2", source = "material2", qualifiedByName = "name")
    @Mapping(target = "material3", source = "material3", qualifiedByName = "name")
    @Mapping(target = "material4", source = "material4", qualifiedByName = "name")
    @Mapping(target = "material5", source = "material5", qualifiedByName = "name")
    BuildTypeDTO toDto(BuildType s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BuildTypeDTO toDtoName(BuildType buildType);
}
