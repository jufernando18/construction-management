package co.com.personalsoft.service.core.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.com.personalsoft.domain.core.impl.DomainBusinessImpl;
import co.com.personalsoft.service.BuildOrderService;
import co.com.personalsoft.service.BuildTypeService;
import co.com.personalsoft.service.CitadelService;
import co.com.personalsoft.service.MaterialService;
import co.com.personalsoft.service.RequisitionService;
import co.com.personalsoft.service.core.Business;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.dto.CitadelDTO;
import co.com.personalsoft.service.dto.MaterialDTO;
import co.com.personalsoft.service.dto.RequisitionDTO;

/**
 * BusinessImpl
 */
@Service
public class BusinessImpl extends DomainBusinessImpl implements Business {
  
  private final RequisitionService requisitionService;
    
  private final MaterialService materialService;

  private final CitadelService citadelService;
  
  private final BuildOrderService buildOrderService;
  
  private final BuildTypeService buildTypeService; 
  
  public BusinessImpl(RequisitionService requisitionService, MaterialService materialService, 
      CitadelService citadelService, BuildOrderService buildOrderService, BuildTypeService buildTypeService) {
    this.requisitionService = requisitionService;
    this.materialService = materialService;
    this.citadelService = citadelService;
    this.buildOrderService = buildOrderService;
    this.buildTypeService = buildTypeService;
  }

  @Override
  public Optional<RequisitionDTO> findRequisitionByCoordinate(String coordinate) {
    return requisitionService.findByCoordinate(coordinate);
  }

  @Override
  public Optional<MaterialDTO> findMaterialById(Long id) {
    return materialService.findOne(id);
  }

  @Override
  public Optional<BuildTypeDTO> findBuildTypeById(Long id) {
    return buildTypeService.findOne(id);
  }

  @Override
  public Optional<CitadelDTO> findCitadelById(Long id) {
    return citadelService.findOne(id);
  }

  @Override
  public RequisitionDTO saveRequisition(RequisitionDTO requisiton) {
    return requisitionService.save(requisiton);
  }

  @Override
  public MaterialDTO saveMaterial(MaterialDTO material) {
    return materialService.save(material);
  }

  @Override
  public BuildOrderDTO saveBuildOrder(BuildOrderDTO buildOrder) {
    return buildOrderService.save(buildOrder);
  }

  @Override
  public CitadelDTO saveCitadel(CitadelDTO citadel) {
    return citadelService.save(citadel);
  }
}