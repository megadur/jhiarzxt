import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPStatus, PStatus } from '../p-status.model';

import { PStatusService } from './p-status.service';

describe('Service Tests', () => {
  describe('PStatus Service', () => {
    let service: PStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IPStatus;
    let expectedResult: IPStatus | IPStatus[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PStatusService);
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

      it('should create a PStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PStatus', () => {
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

      it('should partial update a PStatus', () => {
        const patchObject = Object.assign(
          {
            beschreibung: 'BBBBBB',
          },
          new PStatus()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PStatus', () => {
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

      it('should delete a PStatus', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPStatusToCollectionIfMissing', () => {
        it('should add a PStatus to an empty array', () => {
          const pStatus: IPStatus = { id: 123 };
          expectedResult = service.addPStatusToCollectionIfMissing([], pStatus);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pStatus);
        });

        it('should not add a PStatus to an array that contains it', () => {
          const pStatus: IPStatus = { id: 123 };
          const pStatusCollection: IPStatus[] = [
            {
              ...pStatus,
            },
            { id: 456 },
          ];
          expectedResult = service.addPStatusToCollectionIfMissing(pStatusCollection, pStatus);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PStatus to an array that doesn't contain it", () => {
          const pStatus: IPStatus = { id: 123 };
          const pStatusCollection: IPStatus[] = [{ id: 456 }];
          expectedResult = service.addPStatusToCollectionIfMissing(pStatusCollection, pStatus);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pStatus);
        });

        it('should add only unique PStatus to an array', () => {
          const pStatusArray: IPStatus[] = [{ id: 123 }, { id: 456 }, { id: 18972 }];
          const pStatusCollection: IPStatus[] = [{ id: 123 }];
          expectedResult = service.addPStatusToCollectionIfMissing(pStatusCollection, ...pStatusArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pStatus: IPStatus = { id: 123 };
          const pStatus2: IPStatus = { id: 456 };
          expectedResult = service.addPStatusToCollectionIfMissing([], pStatus, pStatus2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pStatus);
          expect(expectedResult).toContain(pStatus2);
        });

        it('should accept null and undefined values', () => {
          const pStatus: IPStatus = { id: 123 };
          expectedResult = service.addPStatusToCollectionIfMissing([], null, pStatus, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pStatus);
        });

        it('should return initial array if no PStatus is added', () => {
          const pStatusCollection: IPStatus[] = [{ id: 123 }];
          expectedResult = service.addPStatusToCollectionIfMissing(pStatusCollection, undefined, null);
          expect(expectedResult).toEqual(pStatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
