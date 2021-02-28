import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RequisitionComponent } from '../list/requisition.component';
import { RequisitionDetailComponent } from '../detail/requisition-detail.component';
import { RequisitionUpdateComponent } from '../update/requisition-update.component';
import { RequisitionRoutingResolveService } from './requisition-routing-resolve.service';

const requisitionRoute: Routes = [
  {
    path: '',
    component: RequisitionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequisitionDetailComponent,
    resolve: {
      requisition: RequisitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequisitionUpdateComponent,
    resolve: {
      requisition: RequisitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequisitionUpdateComponent,
    resolve: {
      requisition: RequisitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(requisitionRoute)],
  exports: [RouterModule],
})
export class RequisitionRoutingModule {}
