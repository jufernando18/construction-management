package co.com.personalsoft.service.core;

import co.com.personalsoft.service.core.errors.EntityBusinessException;
import co.com.personalsoft.service.dto.RequisitionDTO;

/**
 * Business
 */
public interface Business {

  RequisitionDTO verifyAndSaveRequisition(RequisitionDTO requisition) throws EntityBusinessException;
}