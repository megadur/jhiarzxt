import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMArtikel, getMArtikelIdentifier } from '../m-artikel.model';

export type EntityResponseType = HttpResponse<IMArtikel>;
export type EntityArrayResponseType = HttpResponse<IMArtikel[]>;

@Injectable({ providedIn: 'root' })
export class MArtikelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/m-artikels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mArtikel: IMArtikel): Observable<EntityResponseType> {
    return this.http.post<IMArtikel>(this.resourceUrl, mArtikel, { observe: 'response' });
  }

  update(mArtikel: IMArtikel): Observable<EntityResponseType> {
    return this.http.put<IMArtikel>(`${this.resourceUrl}/${getMArtikelIdentifier(mArtikel) as number}`, mArtikel, { observe: 'response' });
  }

  partialUpdate(mArtikel: IMArtikel): Observable<EntityResponseType> {
    return this.http.patch<IMArtikel>(`${this.resourceUrl}/${getMArtikelIdentifier(mArtikel) as number}`, mArtikel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMArtikel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMArtikel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMArtikelToCollectionIfMissing(mArtikelCollection: IMArtikel[], ...mArtikelsToCheck: (IMArtikel | null | undefined)[]): IMArtikel[] {
    const mArtikels: IMArtikel[] = mArtikelsToCheck.filter(isPresent);
    if (mArtikels.length > 0) {
      const mArtikelCollectionIdentifiers = mArtikelCollection.map(mArtikelItem => getMArtikelIdentifier(mArtikelItem)!);
      const mArtikelsToAdd = mArtikels.filter(mArtikelItem => {
        const mArtikelIdentifier = getMArtikelIdentifier(mArtikelItem);
        if (mArtikelIdentifier == null || mArtikelCollectionIdentifiers.includes(mArtikelIdentifier)) {
          return false;
        }
        mArtikelCollectionIdentifiers.push(mArtikelIdentifier);
        return true;
      });
      return [...mArtikelsToAdd, ...mArtikelCollection];
    }
    return mArtikelCollection;
  }
}
