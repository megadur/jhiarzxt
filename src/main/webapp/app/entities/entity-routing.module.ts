import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'muster-16',
        data: { pageTitle: 'Muster16s' },
        loadChildren: () => import('./muster-16/muster-16.module').then(m => m.Muster16Module),
      },
      {
        path: 'muster-16-ver',
        data: { pageTitle: 'Muster16Vers' },
        loadChildren: () => import('./muster-16-ver/muster-16-ver.module').then(m => m.Muster16VerModule),
      },
      {
        path: 'muster-16-abg',
        data: { pageTitle: 'Muster16Abgs' },
        loadChildren: () => import('./muster-16-abg/muster-16-abg.module').then(m => m.Muster16AbgModule),
      },
      {
        path: 'muster-16-abr',
        data: { pageTitle: 'Muster16Abrs' },
        loadChildren: () => import('./muster-16-abr/muster-16-abr.module').then(m => m.Muster16AbrModule),
      },
      {
        path: 'm-artikel',
        data: { pageTitle: 'MArtikels' },
        loadChildren: () => import('./m-artikel/m-artikel.module').then(m => m.MArtikelModule),
      },
      {
        path: 'm-16-status',
        data: { pageTitle: 'M16Statuses' },
        loadChildren: () => import('./m-16-status/m-16-status.module').then(m => m.M16StatusModule),
      },
      {
        path: 'p-rezept',
        data: { pageTitle: 'PRezepts' },
        loadChildren: () => import('./p-rezept/p-rezept.module').then(m => m.PRezeptModule),
      },
      {
        path: 'p-rezept-ver',
        data: { pageTitle: 'PRezeptVers' },
        loadChildren: () => import('./p-rezept-ver/p-rezept-ver.module').then(m => m.PRezeptVerModule),
      },
      {
        path: 'p-rezept-abg',
        data: { pageTitle: 'PRezeptAbgs' },
        loadChildren: () => import('./p-rezept-abg/p-rezept-abg.module').then(m => m.PRezeptAbgModule),
      },
      {
        path: 'p-rezept-abr',
        data: { pageTitle: 'PRezeptAbrs' },
        loadChildren: () => import('./p-rezept-abr/p-rezept-abr.module').then(m => m.PRezeptAbrModule),
      },
      {
        path: 'p-charge',
        data: { pageTitle: 'PCharges' },
        loadChildren: () => import('./p-charge/p-charge.module').then(m => m.PChargeModule),
      },
      {
        path: 'p-wirkstoff',
        data: { pageTitle: 'PWirkstoffs' },
        loadChildren: () => import('./p-wirkstoff/p-wirkstoff.module').then(m => m.PWirkstoffModule),
      },
      {
        path: 'p-status-info',
        data: { pageTitle: 'PStatusInfos' },
        loadChildren: () => import('./p-status-info/p-status-info.module').then(m => m.PStatusInfoModule),
      },
      {
        path: 'p-status',
        data: { pageTitle: 'PStatuses' },
        loadChildren: () => import('./p-status/p-status.module').then(m => m.PStatusModule),
      },
      {
        path: 'apotheke',
        data: { pageTitle: 'Apothekes' },
        loadChildren: () => import('./apotheke/apotheke.module').then(m => m.ApothekeModule),
      },
      {
        path: 'e-aenderung',
        data: { pageTitle: 'EAenderungs' },
        loadChildren: () => import('./e-aenderung/e-aenderung.module').then(m => m.EAenderungModule),
      },
      {
        path: 'e-rezept',
        data: { pageTitle: 'ERezepts' },
        loadChildren: () => import('./e-rezept/e-rezept.module').then(m => m.ERezeptModule),
      },
      {
        path: 'e-status',
        data: { pageTitle: 'EStatuses' },
        loadChildren: () => import('./e-status/e-status.module').then(m => m.EStatusModule),
      },
      {
        path: 'lieferung',
        data: { pageTitle: 'Lieferungs' },
        loadChildren: () => import('./lieferung/lieferung.module').then(m => m.LieferungModule),
      },
      {
        path: 'rechenzentrum',
        data: { pageTitle: 'Rechenzentrums' },
        loadChildren: () => import('./rechenzentrum/rechenzentrum.module').then(m => m.RechenzentrumModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
