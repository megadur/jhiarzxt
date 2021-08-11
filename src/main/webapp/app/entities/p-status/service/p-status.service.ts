import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPStatus, getPStatusIdentifier } from '../p-status.model';

export type EntityResponseType = HttpResponse<IPStatus>;
export type EntityArrayResponseType = HttpResponse<IPStatus[]>;

@Injectable({ providedIn: 'root' })
export class PStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pStatus: IPStatus): Observable<EntityResponseType> {
    return this.http.post<IPStatus>(this.resourceUrl, pStatus, { observe: 'response' });
  }

  update(pStatus: IPStatus): Observable<EntityResponseType> {
    return this.http.put<IPStatus>(`${this.resourceUrl}/${getPStatusIdentifier(pStatus) as number}`, pStatus, { observe: 'response' });
  }

  partialUpdate(pStatus: IPStatus): Observable<EntityResponseType> {
    return this.http.patch<IPStatus>(`${this.resourceUrl}/${getPStatusIdentifier(pStatus) as number}`, pStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPStatusToCollectionIfMissing(pStatusCollection: IPStatus[], ...pStatusesToCheck: (IPStatus | null | undefined)[]): IPStatus[] {
    const pStatuses: IPStatus[] = pStatusesToCheck.filter(isPresent);
    if (pStatuses.length > 0) {
      const pStatusCollectionIdentifiers = pStatusCollection.map(pStatusItem => getPStatusIdentifier(pStatusItem)!);
      const pStatusesToAdd = pStatuses.filter(pStatusItem => {
        const pStatusIdentifier = getPStatusIdentifier(pStatusItem);
        if (pStatusIdentifier == null || pStatusCollectionIdentifiers.includes(pStatusIdentifier)) {
          return false;
        }
        pStatusCollectionIdentifiers.push(pStatusIdentifier);
        return true;
      });
      return [...pStatusesToAdd, ...pStatusCollection];
    }
    return pStatusCollection;
  }
}
