import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEStatus, getEStatusIdentifier } from '../e-status.model';

export type EntityResponseType = HttpResponse<IEStatus>;
export type EntityArrayResponseType = HttpResponse<IEStatus[]>;

@Injectable({ providedIn: 'root' })
export class EStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/e-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eStatus: IEStatus): Observable<EntityResponseType> {
    return this.http.post<IEStatus>(this.resourceUrl, eStatus, { observe: 'response' });
  }

  update(eStatus: IEStatus): Observable<EntityResponseType> {
    return this.http.put<IEStatus>(`${this.resourceUrl}/${getEStatusIdentifier(eStatus) as number}`, eStatus, { observe: 'response' });
  }

  partialUpdate(eStatus: IEStatus): Observable<EntityResponseType> {
    return this.http.patch<IEStatus>(`${this.resourceUrl}/${getEStatusIdentifier(eStatus) as number}`, eStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEStatusToCollectionIfMissing(eStatusCollection: IEStatus[], ...eStatusesToCheck: (IEStatus | null | undefined)[]): IEStatus[] {
    const eStatuses: IEStatus[] = eStatusesToCheck.filter(isPresent);
    if (eStatuses.length > 0) {
      const eStatusCollectionIdentifiers = eStatusCollection.map(eStatusItem => getEStatusIdentifier(eStatusItem)!);
      const eStatusesToAdd = eStatuses.filter(eStatusItem => {
        const eStatusIdentifier = getEStatusIdentifier(eStatusItem);
        if (eStatusIdentifier == null || eStatusCollectionIdentifiers.includes(eStatusIdentifier)) {
          return false;
        }
        eStatusCollectionIdentifiers.push(eStatusIdentifier);
        return true;
      });
      return [...eStatusesToAdd, ...eStatusCollection];
    }
    return eStatusCollection;
  }
}
