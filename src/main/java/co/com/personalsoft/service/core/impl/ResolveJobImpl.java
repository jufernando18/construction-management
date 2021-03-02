package co.com.personalsoft.service.core.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.personalsoft.domain.core.impl.DomainResolveJobImpl;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.BuildOrderService;
import co.com.personalsoft.service.core.ResolveJob;
import co.com.personalsoft.service.dto.BuildOrderDTO;

@Service
public class ResolveJobImpl extends DomainResolveJobImpl implements ResolveJob {

  private final BuildOrderService buildOrderService;
  
  public ResolveJobImpl(BuildOrderService buildOrderService) {
    this.buildOrderService = buildOrderService;
  }

  @Override
  public List<BuildOrderDTO> findBuildOrderByStateAndStart(BuildOrderState state, LocalDate start) {
    return buildOrderService.findByStateAndStart(state, start);
  }

  @Override
  public List<BuildOrderDTO> findBuildOrderByStateAndFinish(BuildOrderState state,
      LocalDate finish) {
    return buildOrderService.findByStateAndFinish(state, finish);
  }

  @Override
  public BuildOrderDTO saveBuildOrder(BuildOrderDTO buildOrder) {
    return buildOrderService.save(buildOrder);
  }
}
