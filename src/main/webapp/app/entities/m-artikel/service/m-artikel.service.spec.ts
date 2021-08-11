import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMArtikel, MArtikel } from '../m-artikel.model';

import { MArtikelService } from './m-artikel.service';

describe('Service Tests', () => {
  describe('MArtikel Service', () => {
    let service: MArtikelService;
    let httpMock: HttpTestingController;
    let elemDefault: IMArtikel;
    let expectedResult: IMArtikel | IMArtikel[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MArtikelService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        mArtikelId: 'AAAAAAA',
        mRezeptId: 'AAAAAAA',
        apoIk: 'AAAAAAA',
        autidem: 'AAAAAAA',
        faktor: 'AAAAAAA',
        hilfsmittelNr: 'AAAAAAA',
        muster16Id: 'AAAAAAA',
        posNr: 'AAAAAAA',
        pzn: 'AAAAAAA',
        taxe: 'AAAAAAA',
        vZeile: 'AAAAAAA',
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

      it('should create a MArtikel', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MArtikel()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MArtikel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            mArtikelId: 'BBBBBB',
            mRezeptId: 'BBBBBB',
            apoIk: 'BBBBBB',
            autidem: 'BBBBBB',
            faktor: 'BBBBBB',
            hilfsmittelNr: 'BBBBBB',
            muster16Id: 'BBBBBB',
            posNr: 'BBBBBB',
            pzn: 'BBBBBB',
            taxe: 'BBBBBB',
            vZeile: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a MArtikel', () => {
        const patchObject = Object.assign(
          {
            mArtikelId: 'BBBBBB',
            apoIk: 'BBBBBB',
            autidem: 'BBBBBB',
            hilfsmittelNr: 'BBBBBB',
            muster16Id: 'BBBBBB',
            taxe: 'BBBBBB',
            vZeile: 'BBBBBB',
          },
          new MArtikel()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MArtikel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            mArtikelId: 'BBBBBB',
            mRezeptId: 'BBBBBB',
            apoIk: 'BBBBBB',
            autidem: 'BBBBBB',
            faktor: 'BBBBBB',
            hilfsmittelNr: 'BBBBBB',
            muster16Id: 'BBBBBB',
            posNr: 'BBBBBB',
            pzn: 'BBBBBB',
            taxe: 'BBBBBB',
            vZeile: 'BBBBBB',
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

      it('should delete a MArtikel', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMArtikelToCollectionIfMissing', () => {
        it('should add a MArtikel to an empty array', () => {
          const mArtikel: IMArtikel = { id: 123 };
          expectedResult = service.addMArtikelToCollectionIfMissing([], mArtikel);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mArtikel);
        });

        it('should not add a MArtikel to an array that contains it', () => {
          const mArtikel: IMArtikel = { id: 123 };
          const mArtikelCollection: IMArtikel[] = [
            {
              ...mArtikel,
            },
            { id: 456 },
          ];
          expectedResult = service.addMArtikelToCollectionIfMissing(mArtikelCollection, mArtikel);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a MArtikel to an array that doesn't contain it", () => {
          const mArtikel: IMArtikel = { id: 123 };
          const mArtikelCollection: IMArtikel[] = [{ id: 456 }];
          expectedResult = service.addMArtikelToCollectionIfMissing(mArtikelCollection, mArtikel);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mArtikel);
        });

        it('should add only unique MArtikel to an array', () => {
          const mArtikelArray: IMArtikel[] = [{ id: 123 }, { id: 456 }, { id: 17751 }];
          const mArtikelCollection: IMArtikel[] = [{ id: 123 }];
          expectedResult = service.addMArtikelToCollectionIfMissing(mArtikelCollection, ...mArtikelArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const mArtikel: IMArtikel = { id: 123 };
          const mArtikel2: IMArtikel = { id: 456 };
          expectedResult = service.addMArtikelToCollectionIfMissing([], mArtikel, mArtikel2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mArtikel);
          expect(expectedResult).toContain(mArtikel2);
        });

        it('should accept null and undefined values', () => {
          const mArtikel: IMArtikel = { id: 123 };
          expectedResult = service.addMArtikelToCollectionIfMissing([], null, mArtikel, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mArtikel);
        });

        it('should return initial array if no MArtikel is added', () => {
          const mArtikelCollection: IMArtikel[] = [{ id: 123 }];
          expectedResult = service.addMArtikelToCollectionIfMissing(mArtikelCollection, undefined, null);
          expect(expectedResult).toEqual(mArtikelCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
