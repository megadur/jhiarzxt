import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILieferung, getLieferungIdentifier } from '../lieferung.model';

export type EntityResponseType = HttpResponse<ILieferung>;
export type EntityArrayResponseType = HttpResponse<ILieferung[]>;

@Injectable({ providedIn: 'root' })
export class LieferungService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lieferungs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lieferung: ILieferung): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lieferung);
    return this.http
      .post<ILieferung>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lieferung: ILieferung): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lieferung);
    return this.http
      .put<ILieferung>(`${this.resourceUrl}/${getLieferungIdentifier(lieferung) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(lieferung: ILieferung): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lieferung);
    return this.http
      .patch<ILieferung>(`${this.resourceUrl}/${getLieferungIdentifier(lieferung) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILieferung>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILieferung[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLieferungToCollectionIfMissing(
    lieferungCollection: ILieferung[],
    ...lieferungsToCheck: (ILieferung | null | undefined)[]
  ): ILieferung[] {
    const lieferungs: ILieferung[] = lieferungsToCheck.filter(isPresent);
    if (lieferungs.length > 0) {
      const lieferungCollectionIdentifiers = lieferungCollection.map(lieferungItem => getLieferungIdentifier(lieferungItem)!);
      const lieferungsToAdd = lieferungs.filter(lieferungItem => {
        const lieferungIdentifier = getLieferungIdentifier(lieferungItem);
        if (lieferungIdentifier == null || lieferungCollectionIdentifiers.includes(lieferungIdentifier)) {
          return false;
        }
        lieferungCollectionIdentifiers.push(lieferungIdentifier);
        return true;
      });
      return [...lieferungsToAdd, ...lieferungCollection];
    }
    return lieferungCollection;
  }

  protected convertDateFromClient(lieferung: ILieferung): ILieferung {
    return Object.assign({}, lieferung, {
      datum: lieferung.datum?.isValid() ? lieferung.datum.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datum = res.body.datum ? dayjs(res.body.datum) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lieferung: ILieferung) => {
        lieferung.datum = lieferung.datum ? dayjs(lieferung.datum) : undefined;
      });
    }
    return res;
  }
}
