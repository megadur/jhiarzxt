import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILieferung, Lieferung } from '../lieferung.model';
import { LieferungService } from '../service/lieferung.service';

@Injectable({ providedIn: 'root' })
export class LieferungRoutingResolveService implements Resolve<ILieferung> {
  constructor(protected service: LieferungService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILieferung> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lieferung: HttpResponse<Lieferung>) => {
          if (lieferung.body) {
            return of(lieferung.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lieferung());
  }
}
