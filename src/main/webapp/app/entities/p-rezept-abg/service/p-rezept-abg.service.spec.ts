import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPRezeptAbg, PRezeptAbg } from '../p-rezept-abg.model';

import { PRezeptAbgService } from './p-rezept-abg.service';

describe('Service Tests', () => {
  describe('PRezeptAbg Service', () => {
    let service: PRezeptAbgService;
    let httpMock: HttpTestingController;
    let elemDefault: IPRezeptAbg;
    let expectedResult: IPRezeptAbg | IPRezeptAbg[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PRezeptAbgService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pRezeptId: 'AAAAAAA',
        gebFrei: 'AAAAAAA',
        gesBrutto: 'AAAAAAA',
        hashCode: 'AAAAAAA',
        kArt: 'AAAAAAA',
        noctu: 'AAAAAAA',
        pRezeptTyp: 'AAAAAAA',
        rTyp: 'AAAAAAA',
        sonstige: 'AAAAAAA',
        sprStBedarf: 'AAAAAAA',
        verDat: 'AAAAAAA',
        vkGueltigBis: 'AAAAAAA',
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

      it('should create a PRezeptAbg', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PRezeptAbg()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PRezeptAbg', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId: 'BBBBBB',
            gebFrei: 'BBBBBB',
            gesBrutto: 'BBBBBB',
            hashCode: 'BBBBBB',
            kArt: 'BBBBBB',
            noctu: 'BBBBBB',
            pRezeptTyp: 'BBBBBB',
            rTyp: 'BBBBBB',
            sonstige: 'BBBBBB',
            sprStBedarf: 'BBBBBB',
            verDat: 'BBBBBB',
            vkGueltigBis: 'BBBBBB',
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

      it('should partial update a PRezeptAbg', () => {
        const patchObject = Object.assign(
          {
            pRezeptId: 'BBBBBB',
            hashCode: 'BBBBBB',
            kArt: 'BBBBBB',
            pRezeptTyp: 'BBBBBB',
            rTyp: 'BBBBBB',
            sprStBedarf: 'BBBBBB',
            verDat: 'BBBBBB',
            vkGueltigBis: 'BBBBBB',
          },
          new PRezeptAbg()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PRezeptAbg', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId: 'BBBBBB',
            gebFrei: 'BBBBBB',
            gesBrutto: 'BBBBBB',
            hashCode: 'BBBBBB',
            kArt: 'BBBBBB',
            noctu: 'BBBBBB',
            pRezeptTyp: 'BBBBBB',
            rTyp: 'BBBBBB',
            sonstige: 'BBBBBB',
            sprStBedarf: 'BBBBBB',
            verDat: 'BBBBBB',
            vkGueltigBis: 'BBBBBB',
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

      it('should delete a PRezeptAbg', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPRezeptAbgToCollectionIfMissing', () => {
        it('should add a PRezeptAbg to an empty array', () => {
          const pRezeptAbg: IPRezeptAbg = { id: 123 };
          expectedResult = service.addPRezeptAbgToCollectionIfMissing([], pRezeptAbg);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezeptAbg);
        });

        it('should not add a PRezeptAbg to an array that contains it', () => {
          const pRezeptAbg: IPRezeptAbg = { id: 123 };
          const pRezeptAbgCollection: IPRezeptAbg[] = [
            {
              ...pRezeptAbg,
            },
            { id: 456 },
          ];
          expectedResult = service.addPRezeptAbgToCollectionIfMissing(pRezeptAbgCollection, pRezeptAbg);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PRezeptAbg to an array that doesn't contain it", () => {
          const pRezeptAbg: IPRezeptAbg = { id: 123 };
          const pRezeptAbgCollection: IPRezeptAbg[] = [{ id: 456 }];
          expectedResult = service.addPRezeptAbgToCollectionIfMissing(pRezeptAbgCollection, pRezeptAbg);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezeptAbg);
        });

        it('should add only unique PRezeptAbg to an array', () => {
          const pRezeptAbgArray: IPRezeptAbg[] = [{ id: 123 }, { id: 456 }, { id: 9059 }];
          const pRezeptAbgCollection: IPRezeptAbg[] = [{ id: 123 }];
          expectedResult = service.addPRezeptAbgToCollectionIfMissing(pRezeptAbgCollection, ...pRezeptAbgArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pRezeptAbg: IPRezeptAbg = { id: 123 };
          const pRezeptAbg2: IPRezeptAbg = { id: 456 };
          expectedResult = service.addPRezeptAbgToCollectionIfMissing([], pRezeptAbg, pRezeptAbg2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezeptAbg);
          expect(expectedResult).toContain(pRezeptAbg2);
        });

        it('should accept null and undefined values', () => {
          const pRezeptAbg: IPRezeptAbg = { id: 123 };
          expectedResult = service.addPRezeptAbgToCollectionIfMissing([], null, pRezeptAbg, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezeptAbg);
        });

        it('should return initial array if no PRezeptAbg is added', () => {
          const pRezeptAbgCollection: IPRezeptAbg[] = [{ id: 123 }];
          expectedResult = service.addPRezeptAbgToCollectionIfMissing(pRezeptAbgCollection, undefined, null);
          expect(expectedResult).toEqual(pRezeptAbgCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
