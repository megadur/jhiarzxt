import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IApotheke, Apotheke } from '../apotheke.model';

import { ApothekeService } from './apotheke.service';

describe('Service Tests', () => {
  describe('Apotheke Service', () => {
    let service: ApothekeService;
    let httpMock: HttpTestingController;
    let elemDefault: IApotheke;
    let expectedResult: IApotheke | IApotheke[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ApothekeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        iK: 0,
        inhaber: 'AAAAAAA',
        countryCode: 'AAAAAAA',
        plz: 'AAAAAAA',
        ort: 'AAAAAAA',
        str: 'AAAAAAA',
        hausNr: 'AAAAAAA',
        addrZusatz: 'AAAAAAA',
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

      it('should create a Apotheke', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Apotheke()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Apotheke', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iK: 1,
            inhaber: 'BBBBBB',
            countryCode: 'BBBBBB',
            plz: 'BBBBBB',
            ort: 'BBBBBB',
            str: 'BBBBBB',
            hausNr: 'BBBBBB',
            addrZusatz: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Apotheke', () => {
        const patchObject = Object.assign(
          {
            iK: 1,
            inhaber: 'BBBBBB',
            addrZusatz: 'BBBBBB',
          },
          new Apotheke()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Apotheke', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iK: 1,
            inhaber: 'BBBBBB',
            countryCode: 'BBBBBB',
            plz: 'BBBBBB',
            ort: 'BBBBBB',
            str: 'BBBBBB',
            hausNr: 'BBBBBB',
            addrZusatz: 'BBBBBB',
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

      it('should delete a Apotheke', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addApothekeToCollectionIfMissing', () => {
        it('should add a Apotheke to an empty array', () => {
          const apotheke: IApotheke = { id: 123 };
          expectedResult = service.addApothekeToCollectionIfMissing([], apotheke);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(apotheke);
        });

        it('should not add a Apotheke to an array that contains it', () => {
          const apotheke: IApotheke = { id: 123 };
          const apothekeCollection: IApotheke[] = [
            {
              ...apotheke,
            },
            { id: 456 },
          ];
          expectedResult = service.addApothekeToCollectionIfMissing(apothekeCollection, apotheke);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Apotheke to an array that doesn't contain it", () => {
          const apotheke: IApotheke = { id: 123 };
          const apothekeCollection: IApotheke[] = [{ id: 456 }];
          expectedResult = service.addApothekeToCollectionIfMissing(apothekeCollection, apotheke);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(apotheke);
        });

        it('should add only unique Apotheke to an array', () => {
          const apothekeArray: IApotheke[] = [{ id: 123 }, { id: 456 }, { id: 82493 }];
          const apothekeCollection: IApotheke[] = [{ id: 123 }];
          expectedResult = service.addApothekeToCollectionIfMissing(apothekeCollection, ...apothekeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const apotheke: IApotheke = { id: 123 };
          const apotheke2: IApotheke = { id: 456 };
          expectedResult = service.addApothekeToCollectionIfMissing([], apotheke, apotheke2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(apotheke);
          expect(expectedResult).toContain(apotheke2);
        });

        it('should accept null and undefined values', () => {
          const apotheke: IApotheke = { id: 123 };
          expectedResult = service.addApothekeToCollectionIfMissing([], null, apotheke, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(apotheke);
        });

        it('should return initial array if no Apotheke is added', () => {
          const apothekeCollection: IApotheke[] = [{ id: 123 }];
          expectedResult = service.addApothekeToCollectionIfMissing(apothekeCollection, undefined, null);
          expect(expectedResult).toEqual(apothekeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
