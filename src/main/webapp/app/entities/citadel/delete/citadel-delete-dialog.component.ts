import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICitadel } from '../citadel.model';
import { CitadelService } from '../service/citadel.service';

@Component({
  templateUrl: './citadel-delete-dialog.component.html',
})
export class CitadelDeleteDialogComponent {
  citadel?: ICitadel;

  constructor(protected citadelService: CitadelService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.citadelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
