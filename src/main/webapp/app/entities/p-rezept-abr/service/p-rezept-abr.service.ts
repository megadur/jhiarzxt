import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPRezeptAbr, getPRezeptAbrIdentifier } from '../p-rezept-abr.model';

export type EntityResponseType = HttpResponse<IPRezeptAbr>;
export type EntityArrayResponseType = HttpResponse<IPRezeptAbr[]>;

@Injectable({ providedIn: 'root' })
export class PRezeptAbrService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-rezept-abrs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pRezeptAbr: IPRezeptAbr): Observable<EntityResponseType> {
    return this.http.post<IPRezeptAbr>(this.resourceUrl, pRezeptAbr, { observe: 'response' });
  }

  update(pRezeptAbr: IPRezeptAbr): Observable<EntityResponseType> {
    return this.http.put<IPRezeptAbr>(`${this.resourceUrl}/${getPRezeptAbrIdentifier(pRezeptAbr) as number}`, pRezeptAbr, {
      observe: 'response',
    });
  }

  partialUpdate(pRezeptAbr: IPRezeptAbr): Observable<EntityResponseType> {
    return this.http.patch<IPRezeptAbr>(`${this.resourceUrl}/${getPRezeptAbrIdentifier(pRezeptAbr) as number}`, pRezeptAbr, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPRezeptAbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPRezeptAbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPRezeptAbrToCollectionIfMissing(
    pRezeptAbrCollection: IPRezeptAbr[],
    ...pRezeptAbrsToCheck: (IPRezeptAbr | null | undefined)[]
  ): IPRezeptAbr[] {
    const pRezeptAbrs: IPRezeptAbr[] = pRezeptAbrsToCheck.filter(isPresent);
    if (pRezeptAbrs.length > 0) {
      const pRezeptAbrCollectionIdentifiers = pRezeptAbrCollection.map(pRezeptAbrItem => getPRezeptAbrIdentifier(pRezeptAbrItem)!);
      const pRezeptAbrsToAdd = pRezeptAbrs.filter(pRezeptAbrItem => {
        const pRezeptAbrIdentifier = getPRezeptAbrIdentifier(pRezeptAbrItem);
        if (pRezeptAbrIdentifier == null || pRezeptAbrCollectionIdentifiers.includes(pRezeptAbrIdentifier)) {
          return false;
        }
        pRezeptAbrCollectionIdentifiers.push(pRezeptAbrIdentifier);
        return true;
      });
      return [...pRezeptAbrsToAdd, ...pRezeptAbrCollection];
    }
    return pRezeptAbrCollection;
  }
}
