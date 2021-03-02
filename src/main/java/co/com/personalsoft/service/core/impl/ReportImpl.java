package co.com.personalsoft.service.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.com.personalsoft.domain.core.impl.DomainReportImpl;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.BuildOrderService;
import co.com.personalsoft.service.BuildTypeService;
import co.com.personalsoft.service.RequisitionService;
import co.com.personalsoft.service.core.Report;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.dto.RequisitionDTO;

@Service
public class ReportImpl extends DomainReportImpl implements Report {
    
  private final BuildOrderService buildOrderService;
  
  private final BuildTypeService buildTypeService;
  
  private final RequisitionService requisitionService;
  
  public ReportImpl(BuildOrderService buildOrderService, BuildTypeService buildTypeService,
      RequisitionService requisitionService) {
    this.buildOrderService = buildOrderService;
    this.buildTypeService = buildTypeService;
    this.requisitionService = requisitionService;
  }

  @Override
  public List<BuildOrderDTO> findBuildOrderByState(BuildOrderState state) {
    return buildOrderService.findByState(state);
  }
  
  @Override
  public List<BuildOrderDTO> findBuildOrderByStateAndBuildType(BuildOrderState state,
      Long buildTypeId) {
    return buildOrderService.findByStateAndBuildTypeId(state, buildTypeId);
  }

  @Override
  public List<BuildTypeDTO> findBuildTypes() {
    return buildTypeService.findAll();
  }

  @Override
  public Optional<RequisitionDTO> findRequisitionById(Long id) {
    return requisitionService.findOne(id);
  }
  
}
