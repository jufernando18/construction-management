package co.com.personalsoft.service.mapper;

import co.com.personalsoft.domain.*;
import co.com.personalsoft.service.dto.RequisitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requisition} and its DTO {@link RequisitionDTO}.
 */
@Mapper(componentModel = "spring", uses = { BuildTypeMapper.class, CitadelMapper.class })
public interface RequisitionMapper extends EntityMapper<RequisitionDTO, Requisition> {
    @Mapping(target = "buildType", source = "buildType", qualifiedByName = "name")
    @Mapping(target = "citadel", source = "citadel", qualifiedByName = "name")
    RequisitionDTO toDto(Requisition s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RequisitionDTO toDtoName(Requisition requisition);
}
