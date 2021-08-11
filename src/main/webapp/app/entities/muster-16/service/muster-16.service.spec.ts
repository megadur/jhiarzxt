import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMuster16, Muster16 } from '../muster-16.model';

import { Muster16Service } from './muster-16.service';

describe('Service Tests', () => {
  describe('Muster16 Service', () => {
    let service: Muster16Service;
    let httpMock: HttpTestingController;
    let elemDefault: IMuster16;
    let expectedResult: IMuster16 | IMuster16[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Muster16Service);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        mRezeptId: 'AAAAAAA',
        lieferungId: 'AAAAAAA',
        m16Status: 'AAAAAAA',
        mMuster16Id: 'AAAAAAA',
        apoIkSend: 'AAAAAAA',
        apoIkSnd: 'AAAAAAA',
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

      it('should create a Muster16', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Muster16()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Muster16', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            mRezeptId: 'BBBBBB',
            lieferungId: 'BBBBBB',
            m16Status: 'BBBBBB',
            mMuster16Id: 'BBBBBB',
            apoIkSend: 'BBBBBB',
            apoIkSnd: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Muster16', () => {
        const patchObject = Object.assign(
          {
            mRezeptId: 'BBBBBB',
            lieferungId: 'BBBBBB',
            m16Status: 'BBBBBB',
            apoIkSend: 'BBBBBB',
          },
          new Muster16()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Muster16', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            mRezeptId: 'BBBBBB',
            lieferungId: 'BBBBBB',
            m16Status: 'BBBBBB',
            mMuster16Id: 'BBBBBB',
            apoIkSend: 'BBBBBB',
            apoIkSnd: 'BBBBBB',
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

      it('should delete a Muster16', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMuster16ToCollectionIfMissing', () => {
        it('should add a Muster16 to an empty array', () => {
          const muster16: IMuster16 = { id: 123 };
          expectedResult = service.addMuster16ToCollectionIfMissing([], muster16);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16);
        });

        it('should not add a Muster16 to an array that contains it', () => {
          const muster16: IMuster16 = { id: 123 };
          const muster16Collection: IMuster16[] = [
            {
              ...muster16,
            },
            { id: 456 },
          ];
          expectedResult = service.addMuster16ToCollectionIfMissing(muster16Collection, muster16);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Muster16 to an array that doesn't contain it", () => {
          const muster16: IMuster16 = { id: 123 };
          const muster16Collection: IMuster16[] = [{ id: 456 }];
          expectedResult = service.addMuster16ToCollectionIfMissing(muster16Collection, muster16);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16);
        });

        it('should add only unique Muster16 to an array', () => {
          const muster16Array: IMuster16[] = [{ id: 123 }, { id: 456 }, { id: 83922 }];
          const muster16Collection: IMuster16[] = [{ id: 123 }];
          expectedResult = service.addMuster16ToCollectionIfMissing(muster16Collection, ...muster16Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const muster16: IMuster16 = { id: 123 };
          const muster162: IMuster16 = { id: 456 };
          expectedResult = service.addMuster16ToCollectionIfMissing([], muster16, muster162);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16);
          expect(expectedResult).toContain(muster162);
        });

        it('should accept null and undefined values', () => {
          const muster16: IMuster16 = { id: 123 };
          expectedResult = service.addMuster16ToCollectionIfMissing([], null, muster16, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16);
        });

        it('should return initial array if no Muster16 is added', () => {
          const muster16Collection: IMuster16[] = [{ id: 123 }];
          expectedResult = service.addMuster16ToCollectionIfMissing(muster16Collection, undefined, null);
          expect(expectedResult).toEqual(muster16Collection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
