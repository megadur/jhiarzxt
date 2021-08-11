import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IM16Status, M16Status } from '../m-16-status.model';

import { M16StatusService } from './m-16-status.service';

describe('Service Tests', () => {
  describe('M16Status Service', () => {
    let service: M16StatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IM16Status;
    let expectedResult: IM16Status | IM16Status[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(M16StatusService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        m16StatusId: 'AAAAAAA',
        status: 'AAAAAAA',
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

      it('should create a M16Status', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new M16Status()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a M16Status', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            m16StatusId: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a M16Status', () => {
        const patchObject = Object.assign(
          {
            m16StatusId: 'BBBBBB',
            status: 'BBBBBB',
          },
          new M16Status()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of M16Status', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            m16StatusId: 'BBBBBB',
            status: 'BBBBBB',
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

      it('should delete a M16Status', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addM16StatusToCollectionIfMissing', () => {
        it('should add a M16Status to an empty array', () => {
          const m16Status: IM16Status = { id: 123 };
          expectedResult = service.addM16StatusToCollectionIfMissing([], m16Status);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(m16Status);
        });

        it('should not add a M16Status to an array that contains it', () => {
          const m16Status: IM16Status = { id: 123 };
          const m16StatusCollection: IM16Status[] = [
            {
              ...m16Status,
            },
            { id: 456 },
          ];
          expectedResult = service.addM16StatusToCollectionIfMissing(m16StatusCollection, m16Status);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a M16Status to an array that doesn't contain it", () => {
          const m16Status: IM16Status = { id: 123 };
          const m16StatusCollection: IM16Status[] = [{ id: 456 }];
          expectedResult = service.addM16StatusToCollectionIfMissing(m16StatusCollection, m16Status);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(m16Status);
        });

        it('should add only unique M16Status to an array', () => {
          const m16StatusArray: IM16Status[] = [{ id: 123 }, { id: 456 }, { id: 66707 }];
          const m16StatusCollection: IM16Status[] = [{ id: 123 }];
          expectedResult = service.addM16StatusToCollectionIfMissing(m16StatusCollection, ...m16StatusArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const m16Status: IM16Status = { id: 123 };
          const m16Status2: IM16Status = { id: 456 };
          expectedResult = service.addM16StatusToCollectionIfMissing([], m16Status, m16Status2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(m16Status);
          expect(expectedResult).toContain(m16Status2);
        });

        it('should accept null and undefined values', () => {
          const m16Status: IM16Status = { id: 123 };
          expectedResult = service.addM16StatusToCollectionIfMissing([], null, m16Status, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(m16Status);
        });

        it('should return initial array if no M16Status is added', () => {
          const m16StatusCollection: IM16Status[] = [{ id: 123 }];
          expectedResult = service.addM16StatusToCollectionIfMissing(m16StatusCollection, undefined, null);
          expect(expectedResult).toEqual(m16StatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
