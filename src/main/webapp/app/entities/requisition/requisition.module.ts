import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { RequisitionComponent } from './list/requisition.component';
import { RequisitionDetailComponent } from './detail/requisition-detail.component';
import { RequisitionUpdateComponent } from './update/requisition-update.component';
import { RequisitionDeleteDialogComponent } from './delete/requisition-delete-dialog.component';
import { RequisitionRoutingModule } from './route/requisition-routing.module';

@NgModule({
  imports: [SharedModule, RequisitionRoutingModule],
  declarations: [RequisitionComponent, RequisitionDetailComponent, RequisitionUpdateComponent, RequisitionDeleteDialogComponent],
  entryComponents: [RequisitionDeleteDialogComponent],
})
export class RequisitionModule {}
