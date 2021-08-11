import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMuster16Ver, Muster16Ver } from '../muster-16-ver.model';

import { Muster16VerService } from './muster-16-ver.service';

describe('Service Tests', () => {
  describe('Muster16Ver Service', () => {
    let service: Muster16VerService;
    let httpMock: HttpTestingController;
    let elemDefault: IMuster16Ver;
    let expectedResult: IMuster16Ver | IMuster16Ver[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Muster16VerService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        aUnfall: 'AAAAAAA',
        abDatum: 'AAAAAAA',
        vrsNr: 'AAAAAAA',
        vrtrgsArztNr: 'AAAAAAA',
        sprStBedarf: 'AAAAAAA',
        unfall: 'AAAAAAA',
        unfallTag: 'AAAAAAA',
        vGeb: 'AAAAAAA',
        vStat: 'AAAAAAA',
        verDat: 'AAAAAAA',
        kName: 'AAAAAAA',
        kkIk: 'AAAAAAA',
        laNr: 'AAAAAAA',
        noctu: 'AAAAAAA',
        hilf: 'AAAAAAA',
        impf: 'AAAAAAA',
        kArt: 'AAAAAAA',
        rTyp: 'AAAAAAA',
        rezeptTyp: 'AAAAAAA',
        bgrPfl: 'AAAAAAA',
        bvg: 'AAAAAAA',
        eigBet: 'AAAAAAA',
        gebFrei: 'AAAAAAA',
        sonstige: 'AAAAAAA',
        vkGueltigBis: 'AAAAAAA',
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

      it('should create a Muster16Ver', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Muster16Ver()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Muster16Ver', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            aUnfall: 'BBBBBB',
            abDatum: 'BBBBBB',
            vrsNr: 'BBBBBB',
            vrtrgsArztNr: 'BBBBBB',
            sprStBedarf: 'BBBBBB',
            unfall: 'BBBBBB',
            unfallTag: 'BBBBBB',
            vGeb: 'BBBBBB',
            vStat: 'BBBBBB',
            verDat: 'BBBBBB',
            kName: 'BBBBBB',
            kkIk: 'BBBBBB',
            laNr: 'BBBBBB',
            noctu: 'BBBBBB',
            hilf: 'BBBBBB',
            impf: 'BBBBBB',
            kArt: 'BBBBBB',
            rTyp: 'BBBBBB',
            rezeptTyp: 'BBBBBB',
            bgrPfl: 'BBBBBB',
            bvg: 'BBBBBB',
            eigBet: 'BBBBBB',
            gebFrei: 'BBBBBB',
            sonstige: 'BBBBBB',
            vkGueltigBis: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Muster16Ver', () => {
        const patchObject = Object.assign(
          {
            aUnfall: 'BBBBBB',
            vrtrgsArztNr: 'BBBBBB',
            unfall: 'BBBBBB',
            unfallTag: 'BBBBBB',
            vStat: 'BBBBBB',
            verDat: 'BBBBBB',
            kkIk: 'BBBBBB',
            laNr: 'BBBBBB',
            kArt: 'BBBBBB',
            bgrPfl: 'BBBBBB',
            eigBet: 'BBBBBB',
            sonstige: 'BBBBBB',
          },
          new Muster16Ver()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Muster16Ver', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            aUnfall: 'BBBBBB',
            abDatum: 'BBBBBB',
            vrsNr: 'BBBBBB',
            vrtrgsArztNr: 'BBBBBB',
            sprStBedarf: 'BBBBBB',
            unfall: 'BBBBBB',
            unfallTag: 'BBBBBB',
            vGeb: 'BBBBBB',
            vStat: 'BBBBBB',
            verDat: 'BBBBBB',
            kName: 'BBBBBB',
            kkIk: 'BBBBBB',
            laNr: 'BBBBBB',
            noctu: 'BBBBBB',
            hilf: 'BBBBBB',
            impf: 'BBBBBB',
            kArt: 'BBBBBB',
            rTyp: 'BBBBBB',
            rezeptTyp: 'BBBBBB',
            bgrPfl: 'BBBBBB',
            bvg: 'BBBBBB',
            eigBet: 'BBBBBB',
            gebFrei: 'BBBBBB',
            sonstige: 'BBBBBB',
            vkGueltigBis: 'BBBBBB',
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

      it('should delete a Muster16Ver', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMuster16VerToCollectionIfMissing', () => {
        it('should add a Muster16Ver to an empty array', () => {
          const muster16Ver: IMuster16Ver = { id: 123 };
          expectedResult = service.addMuster16VerToCollectionIfMissing([], muster16Ver);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16Ver);
        });

        it('should not add a Muster16Ver to an array that contains it', () => {
          const muster16Ver: IMuster16Ver = { id: 123 };
          const muster16VerCollection: IMuster16Ver[] = [
            {
              ...muster16Ver,
            },
            { id: 456 },
          ];
          expectedResult = service.addMuster16VerToCollectionIfMissing(muster16VerCollection, muster16Ver);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Muster16Ver to an array that doesn't contain it", () => {
          const muster16Ver: IMuster16Ver = { id: 123 };
          const muster16VerCollection: IMuster16Ver[] = [{ id: 456 }];
          expectedResult = service.addMuster16VerToCollectionIfMissing(muster16VerCollection, muster16Ver);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16Ver);
        });

        it('should add only unique Muster16Ver to an array', () => {
          const muster16VerArray: IMuster16Ver[] = [{ id: 123 }, { id: 456 }, { id: 43655 }];
          const muster16VerCollection: IMuster16Ver[] = [{ id: 123 }];
          expectedResult = service.addMuster16VerToCollectionIfMissing(muster16VerCollection, ...muster16VerArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const muster16Ver: IMuster16Ver = { id: 123 };
          const muster16Ver2: IMuster16Ver = { id: 456 };
          expectedResult = service.addMuster16VerToCollectionIfMissing([], muster16Ver, muster16Ver2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16Ver);
          expect(expectedResult).toContain(muster16Ver2);
        });

        it('should accept null and undefined values', () => {
          const muster16Ver: IMuster16Ver = { id: 123 };
          expectedResult = service.addMuster16VerToCollectionIfMissing([], null, muster16Ver, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16Ver);
        });

        it('should return initial array if no Muster16Ver is added', () => {
          const muster16VerCollection: IMuster16Ver[] = [{ id: 123 }];
          expectedResult = service.addMuster16VerToCollectionIfMissing(muster16VerCollection, undefined, null);
          expect(expectedResult).toEqual(muster16VerCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
