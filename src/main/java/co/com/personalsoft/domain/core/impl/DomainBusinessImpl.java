package co.com.personalsoft.domain.core.impl;

import java.time.LocalDate;
import java.util.Optional;

import co.com.personalsoft.domain.core.DomainBusiness;
import co.com.personalsoft.domain.core.shared.ErrorMsg;
import co.com.personalsoft.domain.core.shared.Pair;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.domain.enumeration.RequisitionState;
import co.com.personalsoft.service.core.errors.EntityBusinessException;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.dto.CitadelDTO;
import co.com.personalsoft.service.dto.MaterialDTO;
import co.com.personalsoft.service.dto.RequisitionDTO;

public abstract class DomainBusinessImpl implements DomainBusiness {

  @Override
  public RequisitionDTO verifyAndSaveRequisition(RequisitionDTO requisition) throws EntityBusinessException {
    verifyRelationships(requisition);
    verifyCoordinate(requisition.getCoordinate());
    BuildTypeDTO buildType = requisition.getBuildType();
    CitadelDTO citadel = requisition.getCitadel();
    verifyMaterialsAvailableAndUpdate(buildType);
    LocalDate now = LocalDate.now();
    LocalDate base = citadel.getFinish().isAfter(now) ? citadel.getFinish() : now;
    LocalDate start = base.plusDays(1);
    LocalDate finish = start.plusDays(buildType.getDuration());
    requisition.setState(RequisitionState.APPROVED);
    requisition.setDate(now);
    requisition = saveRequisition(requisition);
    
    createBuildOrderInBackground(requisition, citadel, start, finish);
    return requisition;
  }  
  
  private void createBuildOrderInBackground(RequisitionDTO requisition, CitadelDTO citadel, LocalDate start, LocalDate finish) {
    Runnable r = new Runnable() {
      public void run() {
        BuildOrderDTO buildOrder = new BuildOrderDTO();
        buildOrder.setRequisition(requisition);
        buildOrder.setState(BuildOrderState.PENDING);
        buildOrder.setStart(start);
        buildOrder.setFinish(finish);
        saveBuildOrder(buildOrder);
        
        citadel.setFinish(finish);
        saveCitadel(citadel);
      }
    };
    new Thread(r).start();
  }
  
  private void verifyCoordinate(String coordinate) throws EntityBusinessException {
    boolean coordinateAvailable = findRequisitionByCoordinate(coordinate).isEmpty();
    if (!coordinateAvailable) {
      throw new EntityBusinessException(ErrorMsg.COORDINATE_NOT_AVAILABLE);
    }
  }
  
  private Pair<BuildTypeDTO, CitadelDTO> verifyRelationships(RequisitionDTO requisition) throws EntityBusinessException {
    if (requisition.getBuildType().getId() == null) {
      throw new EntityBusinessException(ErrorMsg.HAS_NOT_BUILD_TYPE);
    }
    if (requisition.getCitadel().getId() == null) {
      throw new EntityBusinessException(ErrorMsg.HAS_NOT_CITADEL);
    }
    
    Optional<BuildTypeDTO> oBuildType = findBuildTypeById(requisition.getBuildType().getId());
    Optional<CitadelDTO> oCitadel = findCitadelById(requisition.getCitadel().getId());
    
    if (!oBuildType.isPresent()) {
      throw new EntityBusinessException(ErrorMsg.NOT_VALID_BUILD_TYPE);
    }
    if (!oCitadel.isPresent()) {
      throw new EntityBusinessException(ErrorMsg.NOT_VALID_CITADEL);
    }
    return new Pair<>(oBuildType.get(), oCitadel.get());
  }
  
  private void verifyMaterialsAvailableAndUpdate(BuildTypeDTO buildType) throws EntityBusinessException {
    Pair<Integer, MaterialDTO> amountAndMaterial1 = verifyMaterial(buildType.getAmountMaterial1(), findMaterialById(buildType.getMaterial1().getId()), ErrorMsg.MATERIAL_1_NOT_AVAILABLE);
    Pair<Integer, MaterialDTO> amountAndMaterial2 = verifyMaterial(buildType.getAmountMaterial2(), findMaterialById(buildType.getMaterial2().getId()), ErrorMsg.MATERIAL_2_NOT_AVAILABLE);
    Pair<Integer, MaterialDTO> amountAndMaterial3 = verifyMaterial(buildType.getAmountMaterial3(), findMaterialById(buildType.getMaterial3().getId()), ErrorMsg.MATERIAL_3_NOT_AVAILABLE);
    Pair<Integer, MaterialDTO> amountAndMaterial4 = verifyMaterial(buildType.getAmountMaterial4(), findMaterialById(buildType.getMaterial4().getId()), ErrorMsg.MATERIAL_4_NOT_AVAILABLE);
    Pair<Integer, MaterialDTO> amountAndMaterial5 = verifyMaterial(buildType.getAmountMaterial5(), findMaterialById(buildType.getMaterial5().getId()), ErrorMsg.MATERIAL_5_NOT_AVAILABLE);
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
    saveMaterial(material); 
  }
}
