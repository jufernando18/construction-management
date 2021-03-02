package co.com.personalsoft.domain.core.impl;

import java.time.LocalDate;
import java.util.List;

import co.com.personalsoft.domain.core.DomainResolveJob;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.dto.BuildOrderDTO;

public abstract class DomainResolveJobImpl implements DomainResolveJob {

  @Override
  public void checkAndUpdatePendingBuildOrder() {
    List<BuildOrderDTO> buildOrdersToBuild = findBuildOrderByStateAndStart(BuildOrderState.PENDING, LocalDate.now());
    buildOrdersToBuild.stream().forEach(buildOrder -> {
      buildOrder.setState(BuildOrderState.BUILDING);
      saveBuildOrder(buildOrder);
    });
  }

  @Override
  public void checkAndUpdateBuildingBuildOrder() {
    List<BuildOrderDTO> buildOrdersToFinish = findBuildOrderByStateAndFinish(BuildOrderState.BUILDING, LocalDate.now());
    buildOrdersToFinish.stream().forEach(buildOrder -> {
      buildOrder.setState(BuildOrderState.FINISHED);
      saveBuildOrder(buildOrder);
    });
  }
}
