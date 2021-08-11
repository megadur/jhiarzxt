import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PRezeptAbrComponent } from '../list/p-rezept-abr.component';
import { PRezeptAbrDetailComponent } from '../detail/p-rezept-abr-detail.component';
import { PRezeptAbrUpdateComponent } from '../update/p-rezept-abr-update.component';
import { PRezeptAbrRoutingResolveService } from './p-rezept-abr-routing-resolve.service';

const pRezeptAbrRoute: Routes = [
  {
    path: '',
    component: PRezeptAbrComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PRezeptAbrDetailComponent,
    resolve: {
      pRezeptAbr: PRezeptAbrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PRezeptAbrUpdateComponent,
    resolve: {
      pRezeptAbr: PRezeptAbrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PRezeptAbrUpdateComponent,
    resolve: {
      pRezeptAbr: PRezeptAbrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pRezeptAbrRoute)],
  exports: [RouterModule],
})
export class PRezeptAbrRoutingModule {}
