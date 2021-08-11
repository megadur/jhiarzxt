import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IERezept, ERezept } from '../e-rezept.model';
import { ERezeptService } from '../service/e-rezept.service';

@Injectable({ providedIn: 'root' })
export class ERezeptRoutingResolveService implements Resolve<IERezept> {
  constructor(protected service: ERezeptService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IERezept> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eRezept: HttpResponse<ERezept>) => {
          if (eRezept.body) {
            return of(eRezept.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ERezept());
  }
}
