import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEAenderung, getEAenderungIdentifier } from '../e-aenderung.model';

export type EntityResponseType = HttpResponse<IEAenderung>;
export type EntityArrayResponseType = HttpResponse<IEAenderung[]>;

@Injectable({ providedIn: 'root' })
export class EAenderungService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/e-aenderungs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eAenderung: IEAenderung): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eAenderung);
    return this.http
      .post<IEAenderung>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eAenderung: IEAenderung): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eAenderung);
    return this.http
      .put<IEAenderung>(`${this.resourceUrl}/${getEAenderungIdentifier(eAenderung) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(eAenderung: IEAenderung): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eAenderung);
    return this.http
      .patch<IEAenderung>(`${this.resourceUrl}/${getEAenderungIdentifier(eAenderung) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEAenderung>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEAenderung[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEAenderungToCollectionIfMissing(
    eAenderungCollection: IEAenderung[],
    ...eAenderungsToCheck: (IEAenderung | null | undefined)[]
  ): IEAenderung[] {
    const eAenderungs: IEAenderung[] = eAenderungsToCheck.filter(isPresent);
    if (eAenderungs.length > 0) {
      const eAenderungCollectionIdentifiers = eAenderungCollection.map(eAenderungItem => getEAenderungIdentifier(eAenderungItem)!);
      const eAenderungsToAdd = eAenderungs.filter(eAenderungItem => {
        const eAenderungIdentifier = getEAenderungIdentifier(eAenderungItem);
        if (eAenderungIdentifier == null || eAenderungCollectionIdentifiers.includes(eAenderungIdentifier)) {
          return false;
        }
        eAenderungCollectionIdentifiers.push(eAenderungIdentifier);
        return true;
      });
      return [...eAenderungsToAdd, ...eAenderungCollection];
    }
    return eAenderungCollection;
  }

  protected convertDateFromClient(eAenderung: IEAenderung): IEAenderung {
    return Object.assign({}, eAenderung, {
      datum: eAenderung.datum?.isValid() ? eAenderung.datum.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((eAenderung: IEAenderung) => {
        eAenderung.datum = eAenderung.datum ? dayjs(eAenderung.datum) : undefined;
      });
    }
    return res;
  }
}
