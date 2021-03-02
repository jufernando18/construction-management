package co.com.personalsoft.domain.core;

import java.time.LocalDate;
import java.util.List;

import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.dto.BuildOrderDTO;

public interface DomainResolveJob {
  void checkAndUpdatePendingBuildOrder();

  void checkAndUpdateBuildingBuildOrder();
  
  List<BuildOrderDTO> findBuildOrderByStateAndStart(BuildOrderState state, LocalDate start);
  
  List<BuildOrderDTO> findBuildOrderByStateAndFinish(BuildOrderState state, LocalDate finish);
  
  BuildOrderDTO saveBuildOrder(BuildOrderDTO buildOrder);
}
