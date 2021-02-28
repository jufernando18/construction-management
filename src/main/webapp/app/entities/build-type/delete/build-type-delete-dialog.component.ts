import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBuildType } from '../build-type.model';
import { BuildTypeService } from '../service/build-type.service';

@Component({
  templateUrl: './build-type-delete-dialog.component.html',
})
export class BuildTypeDeleteDialogComponent {
  buildType?: IBuildType;

  constructor(protected buildTypeService: BuildTypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.buildTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
