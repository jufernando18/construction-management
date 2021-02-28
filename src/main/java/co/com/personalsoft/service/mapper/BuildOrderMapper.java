package co.com.personalsoft.service.mapper;

import co.com.personalsoft.domain.*;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuildOrder} and its DTO {@link BuildOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequisitionMapper.class })
public interface BuildOrderMapper extends EntityMapper<BuildOrderDTO, BuildOrder> {
    @Mapping(target = "requisition", source = "requisition", qualifiedByName = "name")
    BuildOrderDTO toDto(BuildOrder s);
}
