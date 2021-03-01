package co.com.personalsoft.service.core.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.com.personalsoft.domain.Citadel;
import co.com.personalsoft.domain.core.shared.Pair;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.domain.enumeration.RequisitionState;
import co.com.personalsoft.service.BuildOrderService;
import co.com.personalsoft.service.CitadelService;
import co.com.personalsoft.service.MaterialService;
import co.com.personalsoft.service.RequisitionQueryService;
import co.com.personalsoft.service.RequisitionService;
import co.com.personalsoft.service.core.Business;
import co.com.personalsoft.service.core.errors.EntityBusinessException;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.dto.CitadelDTO;
import co.com.personalsoft.service.dto.MaterialDTO;
import co.com.personalsoft.service.dto.RequisitionCriteria;
import co.com.personalsoft.service.dto.RequisitionDTO;
import tech.jhipster.service.filter.StringFilter;

/**
 * BusinessImpl
 */
@Service
public class BusinessImpl implements Business {
  
  private final RequisitionService requisitionService;
  
  private final RequisitionQueryService requisitionQueryService;
  
  private final MaterialService materialService;

  private final CitadelService citadelService;
  
  private final BuildOrderService buildOrderService;
  
  public BusinessImpl(RequisitionService requisitionService, RequisitionQueryService requisitionQueryService,
      MaterialService materialService, CitadelService citadelService, BuildOrderService buildOrderService) {
    this.requisitionService = requisitionService;
    this.requisitionQueryService = requisitionQueryService;
    this.materialService = materialService;
    this.citadelService = citadelService;
    this.buildOrderService = buildOrderService;
  }

  public RequisitionDTO verifyAndSaveRequisition(RequisitionDTO requisition) throws EntityBusinessException {
    RequisitionCriteria requisitionCriteria = new RequisitionCriteria();
    StringFilter coordinateCriteria = new StringFilter();
    coordinateCriteria.setEquals(requisition.getCoordinate());
    requisitionCriteria.setCoordinate(coordinateCriteria);
    boolean coordinateAvailable = requisitionQueryService.findByCriteria(requisitionCriteria).isEmpty();
    if (!coordinateAvailable) {
      throw new EntityBusinessException("coordinateNotAvailable");
    }
    BuildTypeDTO buildType = requisition.getBuildType();
    CitadelDTO citadel = requisition.getCitadel();
    verifyMaterialsAvailableAndUpdate(buildType);
    LocalDate now = LocalDate.now();
    LocalDate start = citadel.getFinish().isAfter(now) ? citadel.getFinish() : now;
    LocalDate finish = start.plusDays(buildType.getDuration());
    requisition.setState(RequisitionState.APPROVED);
    requisition.setDate(now);
    requisition = requisitionService.save(requisition);
    
    BuildOrderDTO buildOrder = new BuildOrderDTO();
    buildOrder.setRequisition(requisition);
    buildOrder.setState(BuildOrderState.PENDING);
    buildOrder.setStart(start);
    buildOrder.setFinish(finish);
    buildOrderService.save(buildOrder);
    
    citadel.setFinish(finish);
    citadelService.save(citadel);
    return requisition;
  }  
  
  private void verifyMaterialsAvailableAndUpdate(BuildTypeDTO buildType) throws EntityBusinessException {
    Pair<Integer, MaterialDTO> amountAndMaterial1 = verifyMaterial(buildType.getAmountMaterial1(), materialService.findOne(buildType.getMaterial1().getId()), "material1NotAvailable");
    Pair<Integer, MaterialDTO> amountAndMaterial2 = verifyMaterial(buildType.getAmountMaterial2(), materialService.findOne(buildType.getMaterial2().getId()), "material2NotAvailable");
    Pair<Integer, MaterialDTO> amountAndMaterial3 = verifyMaterial(buildType.getAmountMaterial3(), materialService.findOne(buildType.getMaterial3().getId()), "material3NotAvailable");
    Pair<Integer, MaterialDTO> amountAndMaterial4 = verifyMaterial(buildType.getAmountMaterial4(), materialService.findOne(buildType.getMaterial4().getId()), "material4NotAvailable");
    Pair<Integer, MaterialDTO> amountAndMaterial5 = verifyMaterial(buildType.getAmountMaterial5(), materialService.findOne(buildType.getMaterial5().getId()), "material5NotAvailable");
    updateMaterial(amountAndMaterial1);
    updateMaterial(amountAndMaterial2);
    updateMaterial(amountAndMaterial3);
    updateMaterial(amountAndMaterial4);
    updateMaterial(amountAndMaterial5);
  }
  
  private Pair<Integer, MaterialDTO> verifyMaterial(Integer amountMaterial, Optional<MaterialDTO> oMaterial, String errorKey) throws EntityBusinessException {
    if (!oMaterial.isPresent() || amountMaterial > oMaterial.get().getAmountAvailable()) {
      throw new EntityBusinessException(errorKey);
    }  
    return new Pair<>(amountMaterial, oMaterial.get());
  }
  
  private void updateMaterial(Pair<Integer, MaterialDTO> amountAndMaterial) {
    Integer amountMaterial = amountAndMaterial.left;
    MaterialDTO material = amountAndMaterial.right;
    material.setAmountAvailable(material.getAmountAvailable() - amountMaterial);
    materialService.save(material); 
  }
}