import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CitadelComponent } from './list/citadel.component';
import { CitadelDetailComponent } from './detail/citadel-detail.component';
import { CitadelUpdateComponent } from './update/citadel-update.component';
import { CitadelDeleteDialogComponent } from './delete/citadel-delete-dialog.component';
import { CitadelRoutingModule } from './route/citadel-routing.module';

@NgModule({
  imports: [SharedModule, CitadelRoutingModule],
  declarations: [CitadelComponent, CitadelDetailComponent, CitadelUpdateComponent, CitadelDeleteDialogComponent],
  entryComponents: [CitadelDeleteDialogComponent],
})
export class CitadelModule {}
