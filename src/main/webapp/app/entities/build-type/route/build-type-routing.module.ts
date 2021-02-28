import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BuildTypeComponent } from '../list/build-type.component';
import { BuildTypeDetailComponent } from '../detail/build-type-detail.component';
import { BuildTypeUpdateComponent } from '../update/build-type-update.component';
import { BuildTypeRoutingResolveService } from './build-type-routing-resolve.service';

const buildTypeRoute: Routes = [
  {
    path: '',
    component: BuildTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BuildTypeDetailComponent,
    resolve: {
      buildType: BuildTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BuildTypeUpdateComponent,
    resolve: {
      buildType: BuildTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BuildTypeUpdateComponent,
    resolve: {
      buildType: BuildTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(buildTypeRoute)],
  exports: [RouterModule],
})
export class BuildTypeRoutingModule {}
