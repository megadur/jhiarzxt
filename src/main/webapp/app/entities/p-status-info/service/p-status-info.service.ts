import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPStatusInfo, getPStatusInfoIdentifier } from '../p-status-info.model';

export type EntityResponseType = HttpResponse<IPStatusInfo>;
export type EntityArrayResponseType = HttpResponse<IPStatusInfo[]>;

@Injectable({ providedIn: 'root' })
export class PStatusInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-status-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pStatusInfo: IPStatusInfo): Observable<EntityResponseType> {
    return this.http.post<IPStatusInfo>(this.resourceUrl, pStatusInfo, { observe: 'response' });
  }

  update(pStatusInfo: IPStatusInfo): Observable<EntityResponseType> {
    return this.http.put<IPStatusInfo>(`${this.resourceUrl}/${getPStatusInfoIdentifier(pStatusInfo) as number}`, pStatusInfo, {
      observe: 'response',
    });
  }

  partialUpdate(pStatusInfo: IPStatusInfo): Observable<EntityResponseType> {
    return this.http.patch<IPStatusInfo>(`${this.resourceUrl}/${getPStatusInfoIdentifier(pStatusInfo) as number}`, pStatusInfo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPStatusInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPStatusInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPStatusInfoToCollectionIfMissing(
    pStatusInfoCollection: IPStatusInfo[],
    ...pStatusInfosToCheck: (IPStatusInfo | null | undefined)[]
  ): IPStatusInfo[] {
    const pStatusInfos: IPStatusInfo[] = pStatusInfosToCheck.filter(isPresent);
    if (pStatusInfos.length > 0) {
      const pStatusInfoCollectionIdentifiers = pStatusInfoCollection.map(pStatusInfoItem => getPStatusInfoIdentifier(pStatusInfoItem)!);
      const pStatusInfosToAdd = pStatusInfos.filter(pStatusInfoItem => {
        const pStatusInfoIdentifier = getPStatusInfoIdentifier(pStatusInfoItem);
        if (pStatusInfoIdentifier == null || pStatusInfoCollectionIdentifiers.includes(pStatusInfoIdentifier)) {
          return false;
        }
        pStatusInfoCollectionIdentifiers.push(pStatusInfoIdentifier);
        return true;
      });
      return [...pStatusInfosToAdd, ...pStatusInfoCollection];
    }
    return pStatusInfoCollection;
  }
}
