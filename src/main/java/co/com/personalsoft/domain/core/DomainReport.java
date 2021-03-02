package co.com.personalsoft.domain.core;

import java.util.List;
import java.util.Optional;

import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.dto.RequisitionDTO;
import co.com.personalsoft.service.dto.core.BuidOrderReportDTO;

public interface DomainReport {
  
  BuidOrderReportDTO report();

  List<BuildOrderDTO> findBuildOrderByState(BuildOrderState state);
    
  Optional<RequisitionDTO> findRequisitionById(Long id);
  
  List<BuildOrderDTO> findBuildOrderByStateAndBuildType(BuildOrderState state, Long buildTypeId);
  
  List<BuildTypeDTO> findBuildTypes();
}
