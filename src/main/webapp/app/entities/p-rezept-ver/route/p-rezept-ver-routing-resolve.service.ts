import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPRezeptVer, PRezeptVer } from '../p-rezept-ver.model';
import { PRezeptVerService } from '../service/p-rezept-ver.service';

@Injectable({ providedIn: 'root' })
export class PRezeptVerRoutingResolveService implements Resolve<IPRezeptVer> {
  constructor(protected service: PRezeptVerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPRezeptVer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pRezeptVer: HttpResponse<PRezeptVer>) => {
          if (pRezeptVer.body) {
            return of(pRezeptVer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PRezeptVer());
  }
}
