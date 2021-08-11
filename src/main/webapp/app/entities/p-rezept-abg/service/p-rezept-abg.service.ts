import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPRezeptAbg, getPRezeptAbgIdentifier } from '../p-rezept-abg.model';

export type EntityResponseType = HttpResponse<IPRezeptAbg>;
export type EntityArrayResponseType = HttpResponse<IPRezeptAbg[]>;

@Injectable({ providedIn: 'root' })
export class PRezeptAbgService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-rezept-abgs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pRezeptAbg: IPRezeptAbg): Observable<EntityResponseType> {
    return this.http.post<IPRezeptAbg>(this.resourceUrl, pRezeptAbg, { observe: 'response' });
  }

  update(pRezeptAbg: IPRezeptAbg): Observable<EntityResponseType> {
    return this.http.put<IPRezeptAbg>(`${this.resourceUrl}/${getPRezeptAbgIdentifier(pRezeptAbg) as number}`, pRezeptAbg, {
      observe: 'response',
    });
  }

  partialUpdate(pRezeptAbg: IPRezeptAbg): Observable<EntityResponseType> {
    return this.http.patch<IPRezeptAbg>(`${this.resourceUrl}/${getPRezeptAbgIdentifier(pRezeptAbg) as number}`, pRezeptAbg, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPRezeptAbg>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPRezeptAbg[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPRezeptAbgToCollectionIfMissing(
    pRezeptAbgCollection: IPRezeptAbg[],
    ...pRezeptAbgsToCheck: (IPRezeptAbg | null | undefined)[]
  ): IPRezeptAbg[] {
    const pRezeptAbgs: IPRezeptAbg[] = pRezeptAbgsToCheck.filter(isPresent);
    if (pRezeptAbgs.length > 0) {
      const pRezeptAbgCollectionIdentifiers = pRezeptAbgCollection.map(pRezeptAbgItem => getPRezeptAbgIdentifier(pRezeptAbgItem)!);
      const pRezeptAbgsToAdd = pRezeptAbgs.filter(pRezeptAbgItem => {
        const pRezeptAbgIdentifier = getPRezeptAbgIdentifier(pRezeptAbgItem);
        if (pRezeptAbgIdentifier == null || pRezeptAbgCollectionIdentifiers.includes(pRezeptAbgIdentifier)) {
          return false;
        }
        pRezeptAbgCollectionIdentifiers.push(pRezeptAbgIdentifier);
        return true;
      });
      return [...pRezeptAbgsToAdd, ...pRezeptAbgCollection];
    }
    return pRezeptAbgCollection;
  }
}
