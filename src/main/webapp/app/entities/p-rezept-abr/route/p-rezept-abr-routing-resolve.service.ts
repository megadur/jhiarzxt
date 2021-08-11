import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPRezeptAbr, PRezeptAbr } from '../p-rezept-abr.model';
import { PRezeptAbrService } from '../service/p-rezept-abr.service';

@Injectable({ providedIn: 'root' })
export class PRezeptAbrRoutingResolveService implements Resolve<IPRezeptAbr> {
  constructor(protected service: PRezeptAbrService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPRezeptAbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pRezeptAbr: HttpResponse<PRezeptAbr>) => {
          if (pRezeptAbr.body) {
            return of(pRezeptAbr.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PRezeptAbr());
  }
}
