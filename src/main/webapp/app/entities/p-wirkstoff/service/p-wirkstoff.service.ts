import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPWirkstoff, getPWirkstoffIdentifier } from '../p-wirkstoff.model';

export type EntityResponseType = HttpResponse<IPWirkstoff>;
export type EntityArrayResponseType = HttpResponse<IPWirkstoff[]>;

@Injectable({ providedIn: 'root' })
export class PWirkstoffService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-wirkstoffs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pWirkstoff: IPWirkstoff): Observable<EntityResponseType> {
    return this.http.post<IPWirkstoff>(this.resourceUrl, pWirkstoff, { observe: 'response' });
  }

  update(pWirkstoff: IPWirkstoff): Observable<EntityResponseType> {
    return this.http.put<IPWirkstoff>(`${this.resourceUrl}/${getPWirkstoffIdentifier(pWirkstoff) as number}`, pWirkstoff, {
      observe: 'response',
    });
  }

  partialUpdate(pWirkstoff: IPWirkstoff): Observable<EntityResponseType> {
    return this.http.patch<IPWirkstoff>(`${this.resourceUrl}/${getPWirkstoffIdentifier(pWirkstoff) as number}`, pWirkstoff, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPWirkstoff>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPWirkstoff[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPWirkstoffToCollectionIfMissing(
    pWirkstoffCollection: IPWirkstoff[],
    ...pWirkstoffsToCheck: (IPWirkstoff | null | undefined)[]
  ): IPWirkstoff[] {
    const pWirkstoffs: IPWirkstoff[] = pWirkstoffsToCheck.filter(isPresent);
    if (pWirkstoffs.length > 0) {
      const pWirkstoffCollectionIdentifiers = pWirkstoffCollection.map(pWirkstoffItem => getPWirkstoffIdentifier(pWirkstoffItem)!);
      const pWirkstoffsToAdd = pWirkstoffs.filter(pWirkstoffItem => {
        const pWirkstoffIdentifier = getPWirkstoffIdentifier(pWirkstoffItem);
        if (pWirkstoffIdentifier == null || pWirkstoffCollectionIdentifiers.includes(pWirkstoffIdentifier)) {
          return false;
        }
        pWirkstoffCollectionIdentifiers.push(pWirkstoffIdentifier);
        return true;
      });
      return [...pWirkstoffsToAdd, ...pWirkstoffCollection];
    }
    return pWirkstoffCollection;
  }
}
