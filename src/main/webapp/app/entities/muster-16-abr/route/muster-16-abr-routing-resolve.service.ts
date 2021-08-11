import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMuster16Abr, Muster16Abr } from '../muster-16-abr.model';
import { Muster16AbrService } from '../service/muster-16-abr.service';

@Injectable({ providedIn: 'root' })
export class Muster16AbrRoutingResolveService implements Resolve<IMuster16Abr> {
  constructor(protected service: Muster16AbrService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMuster16Abr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((muster16Abr: HttpResponse<Muster16Abr>) => {
          if (muster16Abr.body) {
            return of(muster16Abr.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Muster16Abr());
  }
}
