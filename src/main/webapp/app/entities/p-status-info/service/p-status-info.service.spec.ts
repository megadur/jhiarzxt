import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPStatusInfo, PStatusInfo } from '../p-status-info.model';

import { PStatusInfoService } from './p-status-info.service';

describe('Service Tests', () => {
  describe('PStatusInfo Service', () => {
    let service: PStatusInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPStatusInfo;
    let expectedResult: IPStatusInfo | IPStatusInfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PStatusInfoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pStatusInfoId: 'AAAAAAA',
        pRezeptId: 'AAAAAAA',
        apoIk: 'AAAAAAA',
        fCode: 'AAAAAAA',
        fHauptFehler: 'AAAAAAA',
        fKommentar: 'AAAAAAA',
        fKurzText: 'AAAAAAA',
        fLangText: 'AAAAAAA',
        fStatus: 'AAAAAAA',
        fTCode: 'AAAAAAA',
        fWert: 'AAAAAAA',
        faktor: 'AAAAAAA',
        fristEnde: 'AAAAAAA',
        gesBrutto: 'AAAAAAA',
        posNr: 'AAAAAAA',
        taxe: 'AAAAAAA',
        zeitpunkt: 'AAAAAAA',
        zuzahlung: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PStatusInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PStatusInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PStatusInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pStatusInfoId: 'BBBBBB',
            pRezeptId: 'BBBBBB',
            apoIk: 'BBBBBB',
            fCode: 'BBBBBB',
            fHauptFehler: 'BBBBBB',
            fKommentar: 'BBBBBB',
            fKurzText: 'BBBBBB',
            fLangText: 'BBBBBB',
            fStatus: 'BBBBBB',
            fTCode: 'BBBBBB',
            fWert: 'BBBBBB',
            faktor: 'BBBBBB',
            fristEnde: 'BBBBBB',
            gesBrutto: 'BBBBBB',
            posNr: 'BBBBBB',
            taxe: 'BBBBBB',
            zeitpunkt: 'BBBBBB',
            zuzahlung: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PStatusInfo', () => {
        const patchObject = Object.assign(
          {
            pStatusInfoId: 'BBBBBB',
            pRezeptId: 'BBBBBB',
            apoIk: 'BBBBBB',
            fCode: 'BBBBBB',
            fLangText: 'BBBBBB',
            fStatus: 'BBBBBB',
            gesBrutto: 'BBBBBB',
            posNr: 'BBBBBB',
            taxe: 'BBBBBB',
          },
          new PStatusInfo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PStatusInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pStatusInfoId: 'BBBBBB',
            pRezeptId: 'BBBBBB',
            apoIk: 'BBBBBB',
            fCode: 'BBBBBB',
            fHauptFehler: 'BBBBBB',
            fKommentar: 'BBBBBB',
            fKurzText: 'BBBBBB',
            fLangText: 'BBBBBB',
            fStatus: 'BBBBBB',
            fTCode: 'BBBBBB',
            fWert: 'BBBBBB',
            faktor: 'BBBBBB',
            fristEnde: 'BBBBBB',
            gesBrutto: 'BBBBBB',
            posNr: 'BBBBBB',
            taxe: 'BBBBBB',
            zeitpunkt: 'BBBBBB',
            zuzahlung: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PStatusInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPStatusInfoToCollectionIfMissing', () => {
        it('should add a PStatusInfo to an empty array', () => {
          const pStatusInfo: IPStatusInfo = { id: 123 };
          expectedResult = service.addPStatusInfoToCollectionIfMissing([], pStatusInfo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pStatusInfo);
        });

        it('should not add a PStatusInfo to an array that contains it', () => {
          const pStatusInfo: IPStatusInfo = { id: 123 };
          const pStatusInfoCollection: IPStatusInfo[] = [
            {
              ...pStatusInfo,
            },
            { id: 456 },
          ];
          expectedResult = service.addPStatusInfoToCollectionIfMissing(pStatusInfoCollection, pStatusInfo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PStatusInfo to an array that doesn't contain it", () => {
          const pStatusInfo: IPStatusInfo = { id: 123 };
          const pStatusInfoCollection: IPStatusInfo[] = [{ id: 456 }];
          expectedResult = service.addPStatusInfoToCollectionIfMissing(pStatusInfoCollection, pStatusInfo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pStatusInfo);
        });

        it('should add only unique PStatusInfo to an array', () => {
          const pStatusInfoArray: IPStatusInfo[] = [{ id: 123 }, { id: 456 }, { id: 83531 }];
          const pStatusInfoCollection: IPStatusInfo[] = [{ id: 123 }];
          expectedResult = service.addPStatusInfoToCollectionIfMissing(pStatusInfoCollection, ...pStatusInfoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pStatusInfo: IPStatusInfo = { id: 123 };
          const pStatusInfo2: IPStatusInfo = { id: 456 };
          expectedResult = service.addPStatusInfoToCollectionIfMissing([], pStatusInfo, pStatusInfo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pStatusInfo);
          expect(expectedResult).toContain(pStatusInfo2);
        });

        it('should accept null and undefined values', () => {
          const pStatusInfo: IPStatusInfo = { id: 123 };
          expectedResult = service.addPStatusInfoToCollectionIfMissing([], null, pStatusInfo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pStatusInfo);
        });

        it('should return initial array if no PStatusInfo is added', () => {
          const pStatusInfoCollection: IPStatusInfo[] = [{ id: 123 }];
          expectedResult = service.addPStatusInfoToCollectionIfMissing(pStatusInfoCollection, undefined, null);
          expect(expectedResult).toEqual(pStatusInfoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
