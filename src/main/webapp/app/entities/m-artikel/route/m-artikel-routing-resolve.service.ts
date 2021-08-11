import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMArtikel, MArtikel } from '../m-artikel.model';
import { MArtikelService } from '../service/m-artikel.service';

@Injectable({ providedIn: 'root' })
export class MArtikelRoutingResolveService implements Resolve<IMArtikel> {
  constructor(protected service: MArtikelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMArtikel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mArtikel: HttpResponse<MArtikel>) => {
          if (mArtikel.body) {
            return of(mArtikel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MArtikel());
  }
}
