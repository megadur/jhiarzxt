import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPRezept, PRezept } from '../p-rezept.model';
import { PRezeptService } from '../service/p-rezept.service';

@Injectable({ providedIn: 'root' })
export class PRezeptRoutingResolveService implements Resolve<IPRezept> {
  constructor(protected service: PRezeptService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPRezept> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pRezept: HttpResponse<PRezept>) => {
          if (pRezept.body) {
            return of(pRezept.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PRezept());
  }
}
