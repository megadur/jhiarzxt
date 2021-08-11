import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ApothekeComponent } from '../list/apotheke.component';
import { ApothekeDetailComponent } from '../detail/apotheke-detail.component';
import { ApothekeUpdateComponent } from '../update/apotheke-update.component';
import { ApothekeRoutingResolveService } from './apotheke-routing-resolve.service';

const apothekeRoute: Routes = [
  {
    path: '',
    component: ApothekeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApothekeDetailComponent,
    resolve: {
      apotheke: ApothekeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApothekeUpdateComponent,
    resolve: {
      apotheke: ApothekeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApothekeUpdateComponent,
    resolve: {
      apotheke: ApothekeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(apothekeRoute)],
  exports: [RouterModule],
})
export class ApothekeRoutingModule {}
