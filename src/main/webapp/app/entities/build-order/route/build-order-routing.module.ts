import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BuildOrderComponent } from '../list/build-order.component';
import { BuildOrderDetailComponent } from '../detail/build-order-detail.component';
import { BuildOrderUpdateComponent } from '../update/build-order-update.component';
import { BuildOrderRoutingResolveService } from './build-order-routing-resolve.service';

const buildOrderRoute: Routes = [
  {
    path: '',
    component: BuildOrderComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BuildOrderDetailComponent,
    resolve: {
      buildOrder: BuildOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BuildOrderUpdateComponent,
    resolve: {
      buildOrder: BuildOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BuildOrderUpdateComponent,
    resolve: {
      buildOrder: BuildOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(buildOrderRoute)],
  exports: [RouterModule],
})
export class BuildOrderRoutingModule {}
