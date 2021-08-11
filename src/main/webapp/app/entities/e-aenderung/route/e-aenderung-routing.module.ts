import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EAenderungComponent } from '../list/e-aenderung.component';
import { EAenderungDetailComponent } from '../detail/e-aenderung-detail.component';
import { EAenderungUpdateComponent } from '../update/e-aenderung-update.component';
import { EAenderungRoutingResolveService } from './e-aenderung-routing-resolve.service';

const eAenderungRoute: Routes = [
  {
    path: '',
    component: EAenderungComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EAenderungDetailComponent,
    resolve: {
      eAenderung: EAenderungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EAenderungUpdateComponent,
    resolve: {
      eAenderung: EAenderungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EAenderungUpdateComponent,
    resolve: {
      eAenderung: EAenderungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eAenderungRoute)],
  exports: [RouterModule],
})
export class EAenderungRoutingModule {}
