import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CitadelComponent } from '../list/citadel.component';
import { CitadelDetailComponent } from '../detail/citadel-detail.component';
import { CitadelUpdateComponent } from '../update/citadel-update.component';
import { CitadelRoutingResolveService } from './citadel-routing-resolve.service';

const citadelRoute: Routes = [
  {
    path: '',
    component: CitadelComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CitadelDetailComponent,
    resolve: {
      citadel: CitadelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CitadelUpdateComponent,
    resolve: {
      citadel: CitadelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CitadelUpdateComponent,
    resolve: {
      citadel: CitadelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(citadelRoute)],
  exports: [RouterModule],
})
export class CitadelRoutingModule {}
