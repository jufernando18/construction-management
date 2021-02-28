import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequisition } from '../requisition.model';
import { RequisitionService } from '../service/requisition.service';

@Component({
  templateUrl: './requisition-delete-dialog.component.html',
})
export class RequisitionDeleteDialogComponent {
  requisition?: IRequisition;

  constructor(protected requisitionService: RequisitionService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requisitionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
