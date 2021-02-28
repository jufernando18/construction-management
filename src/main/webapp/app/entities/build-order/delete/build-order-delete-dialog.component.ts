import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBuildOrder } from '../build-order.model';
import { BuildOrderService } from '../service/build-order.service';

@Component({
  templateUrl: './build-order-delete-dialog.component.html',
})
export class BuildOrderDeleteDialogComponent {
  buildOrder?: IBuildOrder;

  constructor(protected buildOrderService: BuildOrderService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.buildOrderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
