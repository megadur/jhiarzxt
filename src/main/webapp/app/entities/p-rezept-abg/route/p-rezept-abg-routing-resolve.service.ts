import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPRezeptAbg, PRezeptAbg } from '../p-rezept-abg.model';
import { PRezeptAbgService } from '../service/p-rezept-abg.service';

@Injectable({ providedIn: 'root' })
export class PRezeptAbgRoutingResolveService implements Resolve<IPRezeptAbg> {
  constructor(protected service: PRezeptAbgService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPRezeptAbg> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pRezeptAbg: HttpResponse<PRezeptAbg>) => {
          if (pRezeptAbg.body) {
            return of(pRezeptAbg.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PRezeptAbg());
  }
}
