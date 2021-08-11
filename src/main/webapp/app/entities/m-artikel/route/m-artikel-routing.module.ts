import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MArtikelComponent } from '../list/m-artikel.component';
import { MArtikelDetailComponent } from '../detail/m-artikel-detail.component';
import { MArtikelUpdateComponent } from '../update/m-artikel-update.component';
import { MArtikelRoutingResolveService } from './m-artikel-routing-resolve.service';

const mArtikelRoute: Routes = [
  {
    path: '',
    component: MArtikelComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MArtikelDetailComponent,
    resolve: {
      mArtikel: MArtikelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MArtikelUpdateComponent,
    resolve: {
      mArtikel: MArtikelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MArtikelUpdateComponent,
    resolve: {
      mArtikel: MArtikelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mArtikelRoute)],
  exports: [RouterModule],
})
export class MArtikelRoutingModule {}
