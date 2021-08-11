import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PRezeptVerComponent } from '../list/p-rezept-ver.component';
import { PRezeptVerDetailComponent } from '../detail/p-rezept-ver-detail.component';
import { PRezeptVerUpdateComponent } from '../update/p-rezept-ver-update.component';
import { PRezeptVerRoutingResolveService } from './p-rezept-ver-routing-resolve.service';

const pRezeptVerRoute: Routes = [
  {
    path: '',
    component: PRezeptVerComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PRezeptVerDetailComponent,
    resolve: {
      pRezeptVer: PRezeptVerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PRezeptVerUpdateComponent,
    resolve: {
      pRezeptVer: PRezeptVerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PRezeptVerUpdateComponent,
    resolve: {
      pRezeptVer: PRezeptVerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pRezeptVerRoute)],
  exports: [RouterModule],
})
export class PRezeptVerRoutingModule {}
