package co.com.personalsoft.domain.core;

import java.util.Optional;

import co.com.personalsoft.service.core.errors.EntityBusinessException;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.dto.CitadelDTO;
import co.com.personalsoft.service.dto.MaterialDTO;
import co.com.personalsoft.service.dto.RequisitionDTO;

public interface DomainBusiness {

  RequisitionDTO verifyAndSaveRequisition(RequisitionDTO requisition) throws EntityBusinessException;
  
  Optional<RequisitionDTO> findRequisitionByCoordinate(String coordinate);
  
  Optional<MaterialDTO> findMaterialById(Long id);
  
  Optional<BuildTypeDTO> findBuildTypeById(Long id);
  
  Optional<CitadelDTO> findCitadelById(Long id);
  
  RequisitionDTO saveRequisition(RequisitionDTO requisiton);
  
  MaterialDTO saveMaterial(MaterialDTO material);
  
  BuildOrderDTO saveBuildOrder(BuildOrderDTO buildOrder);
  
  CitadelDTO saveCitadel(CitadelDTO citadel);
}
