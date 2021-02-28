package co.com.personalsoft.service.mapper;

import co.com.personalsoft.domain.*;
import co.com.personalsoft.service.dto.CitadelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Citadel} and its DTO {@link CitadelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CitadelMapper extends EntityMapper<CitadelDTO, Citadel> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CitadelDTO toDtoName(Citadel citadel);
}
