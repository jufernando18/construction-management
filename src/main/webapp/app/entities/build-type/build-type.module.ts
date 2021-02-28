import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BuildTypeComponent } from './list/build-type.component';
import { BuildTypeDetailComponent } from './detail/build-type-detail.component';
import { BuildTypeUpdateComponent } from './update/build-type-update.component';
import { BuildTypeDeleteDialogComponent } from './delete/build-type-delete-dialog.component';
import { BuildTypeRoutingModule } from './route/build-type-routing.module';

@NgModule({
  imports: [SharedModule, BuildTypeRoutingModule],
  declarations: [BuildTypeComponent, BuildTypeDetailComponent, BuildTypeUpdateComponent, BuildTypeDeleteDialogComponent],
  entryComponents: [BuildTypeDeleteDialogComponent],
})
export class BuildTypeModule {}
