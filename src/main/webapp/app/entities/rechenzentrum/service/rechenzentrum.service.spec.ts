import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRechenzentrum, Rechenzentrum } from '../rechenzentrum.model';

import { RechenzentrumService } from './rechenzentrum.service';

describe('Service Tests', () => {
  describe('Rechenzentrum Service', () => {
    let service: RechenzentrumService;
    let httpMock: HttpTestingController;
    let elemDefault: IRechenzentrum;
    let expectedResult: IRechenzentrum | IRechenzentrum[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RechenzentrumService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        iD: 'AAAAAAA',
        name: 'AAAAAAA',
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

      it('should create a Rechenzentrum', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Rechenzentrum()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Rechenzentrum', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iD: 'BBBBBB',
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Rechenzentrum', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new Rechenzentrum()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Rechenzentrum', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iD: 'BBBBBB',
            name: 'BBBBBB',
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

      it('should delete a Rechenzentrum', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRechenzentrumToCollectionIfMissing', () => {
        it('should add a Rechenzentrum to an empty array', () => {
          const rechenzentrum: IRechenzentrum = { id: 123 };
          expectedResult = service.addRechenzentrumToCollectionIfMissing([], rechenzentrum);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rechenzentrum);
        });

        it('should not add a Rechenzentrum to an array that contains it', () => {
          const rechenzentrum: IRechenzentrum = { id: 123 };
          const rechenzentrumCollection: IRechenzentrum[] = [
            {
              ...rechenzentrum,
            },
            { id: 456 },
          ];
          expectedResult = service.addRechenzentrumToCollectionIfMissing(rechenzentrumCollection, rechenzentrum);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Rechenzentrum to an array that doesn't contain it", () => {
          const rechenzentrum: IRechenzentrum = { id: 123 };
          const rechenzentrumCollection: IRechenzentrum[] = [{ id: 456 }];
          expectedResult = service.addRechenzentrumToCollectionIfMissing(rechenzentrumCollection, rechenzentrum);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rechenzentrum);
        });

        it('should add only unique Rechenzentrum to an array', () => {
          const rechenzentrumArray: IRechenzentrum[] = [{ id: 123 }, { id: 456 }, { id: 27212 }];
          const rechenzentrumCollection: IRechenzentrum[] = [{ id: 123 }];
          expectedResult = service.addRechenzentrumToCollectionIfMissing(rechenzentrumCollection, ...rechenzentrumArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const rechenzentrum: IRechenzentrum = { id: 123 };
          const rechenzentrum2: IRechenzentrum = { id: 456 };
          expectedResult = service.addRechenzentrumToCollectionIfMissing([], rechenzentrum, rechenzentrum2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rechenzentrum);
          expect(expectedResult).toContain(rechenzentrum2);
        });

        it('should accept null and undefined values', () => {
          const rechenzentrum: IRechenzentrum = { id: 123 };
          expectedResult = service.addRechenzentrumToCollectionIfMissing([], null, rechenzentrum, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rechenzentrum);
        });

        it('should return initial array if no Rechenzentrum is added', () => {
          const rechenzentrumCollection: IRechenzentrum[] = [{ id: 123 }];
          expectedResult = service.addRechenzentrumToCollectionIfMissing(rechenzentrumCollection, undefined, null);
          expect(expectedResult).toEqual(rechenzentrumCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
