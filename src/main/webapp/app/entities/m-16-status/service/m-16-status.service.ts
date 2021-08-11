import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IM16Status, getM16StatusIdentifier } from '../m-16-status.model';

export type EntityResponseType = HttpResponse<IM16Status>;
export type EntityArrayResponseType = HttpResponse<IM16Status[]>;

@Injectable({ providedIn: 'root' })
export class M16StatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/m-16-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(m16Status: IM16Status): Observable<EntityResponseType> {
    return this.http.post<IM16Status>(this.resourceUrl, m16Status, { observe: 'response' });
  }

  update(m16Status: IM16Status): Observable<EntityResponseType> {
    return this.http.put<IM16Status>(`${this.resourceUrl}/${getM16StatusIdentifier(m16Status) as number}`, m16Status, {
      observe: 'response',
    });
  }

  partialUpdate(m16Status: IM16Status): Observable<EntityResponseType> {
    return this.http.patch<IM16Status>(`${this.resourceUrl}/${getM16StatusIdentifier(m16Status) as number}`, m16Status, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IM16Status>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IM16Status[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addM16StatusToCollectionIfMissing(
    m16StatusCollection: IM16Status[],
    ...m16StatusesToCheck: (IM16Status | null | undefined)[]
  ): IM16Status[] {
    const m16Statuses: IM16Status[] = m16StatusesToCheck.filter(isPresent);
    if (m16Statuses.length > 0) {
      const m16StatusCollectionIdentifiers = m16StatusCollection.map(m16StatusItem => getM16StatusIdentifier(m16StatusItem)!);
      const m16StatusesToAdd = m16Statuses.filter(m16StatusItem => {
        const m16StatusIdentifier = getM16StatusIdentifier(m16StatusItem);
        if (m16StatusIdentifier == null || m16StatusCollectionIdentifiers.includes(m16StatusIdentifier)) {
          return false;
        }
        m16StatusCollectionIdentifiers.push(m16StatusIdentifier);
        return true;
      });
      return [...m16StatusesToAdd, ...m16StatusCollection];
    }
    return m16StatusCollection;
  }
}
