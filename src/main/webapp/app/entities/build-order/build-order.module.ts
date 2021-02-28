import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BuildOrderComponent } from './list/build-order.component';
import { BuildOrderDetailComponent } from './detail/build-order-detail.component';
import { BuildOrderUpdateComponent } from './update/build-order-update.component';
import { BuildOrderDeleteDialogComponent } from './delete/build-order-delete-dialog.component';
import { BuildOrderRoutingModule } from './route/build-order-routing.module';

@NgModule({
  imports: [SharedModule, BuildOrderRoutingModule],
  declarations: [BuildOrderComponent, BuildOrderDetailComponent, BuildOrderUpdateComponent, BuildOrderDeleteDialogComponent],
  entryComponents: [BuildOrderDeleteDialogComponent],
})
export class BuildOrderModule {}
