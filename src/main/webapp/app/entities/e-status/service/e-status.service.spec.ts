import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEStatus, EStatus } from '../e-status.model';

import { EStatusService } from './e-status.service';

describe('Service Tests', () => {
  describe('EStatus Service', () => {
    let service: EStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IEStatus;
    let expectedResult: IEStatus | IEStatus[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EStatusService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        wert: 'AAAAAAA',
        beschreibung: 'AAAAAAA',
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

      it('should create a EStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            wert: 'BBBBBB',
            beschreibung: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EStatus', () => {
        const patchObject = Object.assign(
          {
            wert: 'BBBBBB',
            beschreibung: 'BBBBBB',
          },
          new EStatus()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            wert: 'BBBBBB',
            beschreibung: 'BBBBBB',
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

      it('should delete a EStatus', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEStatusToCollectionIfMissing', () => {
        it('should add a EStatus to an empty array', () => {
          const eStatus: IEStatus = { id: 123 };
          expectedResult = service.addEStatusToCollectionIfMissing([], eStatus);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eStatus);
        });

        it('should not add a EStatus to an array that contains it', () => {
          const eStatus: IEStatus = { id: 123 };
          const eStatusCollection: IEStatus[] = [
            {
              ...eStatus,
            },
            { id: 456 },
          ];
          expectedResult = service.addEStatusToCollectionIfMissing(eStatusCollection, eStatus);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EStatus to an array that doesn't contain it", () => {
          const eStatus: IEStatus = { id: 123 };
          const eStatusCollection: IEStatus[] = [{ id: 456 }];
          expectedResult = service.addEStatusToCollectionIfMissing(eStatusCollection, eStatus);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eStatus);
        });

        it('should add only unique EStatus to an array', () => {
          const eStatusArray: IEStatus[] = [{ id: 123 }, { id: 456 }, { id: 90153 }];
          const eStatusCollection: IEStatus[] = [{ id: 123 }];
          expectedResult = service.addEStatusToCollectionIfMissing(eStatusCollection, ...eStatusArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const eStatus: IEStatus = { id: 123 };
          const eStatus2: IEStatus = { id: 456 };
          expectedResult = service.addEStatusToCollectionIfMissing([], eStatus, eStatus2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eStatus);
          expect(expectedResult).toContain(eStatus2);
        });

        it('should accept null and undefined values', () => {
          const eStatus: IEStatus = { id: 123 };
          expectedResult = service.addEStatusToCollectionIfMissing([], null, eStatus, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eStatus);
        });

        it('should return initial array if no EStatus is added', () => {
          const eStatusCollection: IEStatus[] = [{ id: 123 }];
          expectedResult = service.addEStatusToCollectionIfMissing(eStatusCollection, undefined, null);
          expect(expectedResult).toEqual(eStatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
