import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PRezeptAbgComponent } from '../list/p-rezept-abg.component';
import { PRezeptAbgDetailComponent } from '../detail/p-rezept-abg-detail.component';
import { PRezeptAbgUpdateComponent } from '../update/p-rezept-abg-update.component';
import { PRezeptAbgRoutingResolveService } from './p-rezept-abg-routing-resolve.service';

const pRezeptAbgRoute: Routes = [
  {
    path: '',
    component: PRezeptAbgComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PRezeptAbgDetailComponent,
    resolve: {
      pRezeptAbg: PRezeptAbgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PRezeptAbgUpdateComponent,
    resolve: {
      pRezeptAbg: PRezeptAbgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PRezeptAbgUpdateComponent,
    resolve: {
      pRezeptAbg: PRezeptAbgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pRezeptAbgRoute)],
  exports: [RouterModule],
})
export class PRezeptAbgRoutingModule {}
