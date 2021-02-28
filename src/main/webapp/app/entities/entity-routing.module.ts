import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'build-order',
        data: { pageTitle: 'constructionmanagementApp.buildOrder.home.title' },
        loadChildren: () => import('./build-order/build-order.module').then(m => m.BuildOrderModule),
      },
      {
        path: 'build-type',
        data: { pageTitle: 'constructionmanagementApp.buildType.home.title' },
        loadChildren: () => import('./build-type/build-type.module').then(m => m.BuildTypeModule),
      },
      {
        path: 'citadel',
        data: { pageTitle: 'constructionmanagementApp.citadel.home.title' },
        loadChildren: () => import('./citadel/citadel.module').then(m => m.CitadelModule),
      },
      {
        path: 'material',
        data: { pageTitle: 'constructionmanagementApp.material.home.title' },
        loadChildren: () => import('./material/material.module').then(m => m.MaterialModule),
      },
      {
        path: 'requisition',
        data: { pageTitle: 'constructionmanagementApp.requisition.home.title' },
        loadChildren: () => import('./requisition/requisition.module').then(m => m.RequisitionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
