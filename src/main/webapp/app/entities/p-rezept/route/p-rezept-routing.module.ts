import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PRezeptComponent } from '../list/p-rezept.component';
import { PRezeptDetailComponent } from '../detail/p-rezept-detail.component';
import { PRezeptUpdateComponent } from '../update/p-rezept-update.component';
import { PRezeptRoutingResolveService } from './p-rezept-routing-resolve.service';

const pRezeptRoute: Routes = [
  {
    path: '',
    component: PRezeptComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PRezeptDetailComponent,
    resolve: {
      pRezept: PRezeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PRezeptUpdateComponent,
    resolve: {
      pRezept: PRezeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PRezeptUpdateComponent,
    resolve: {
      pRezept: PRezeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pRezeptRoute)],
  exports: [RouterModule],
})
export class PRezeptRoutingModule {}
