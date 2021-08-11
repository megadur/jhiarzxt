import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMuster16Abg, Muster16Abg } from '../muster-16-abg.model';
import { Muster16AbgService } from '../service/muster-16-abg.service';

@Injectable({ providedIn: 'root' })
export class Muster16AbgRoutingResolveService implements Resolve<IMuster16Abg> {
  constructor(protected service: Muster16AbgService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMuster16Abg> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((muster16Abg: HttpResponse<Muster16Abg>) => {
          if (muster16Abg.body) {
            return of(muster16Abg.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Muster16Abg());
  }
}
