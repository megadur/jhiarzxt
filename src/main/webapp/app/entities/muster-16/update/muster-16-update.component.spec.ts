jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { Muster16Service } from '../service/muster-16.service';
import { IMuster16, Muster16 } from '../muster-16.model';
import { IM16Status } from 'app/entities/m-16-status/m-16-status.model';
import { M16StatusService } from 'app/entities/m-16-status/service/m-16-status.service';
import { IMuster16Ver } from 'app/entities/muster-16-ver/muster-16-ver.model';
import { Muster16VerService } from 'app/entities/muster-16-ver/service/muster-16-ver.service';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';
import { LieferungService } from 'app/entities/lieferung/service/lieferung.service';

import { Muster16UpdateComponent } from './muster-16-update.component';

describe('Component Tests', () => {
  describe('Muster16 Management Update Component', () => {
    let comp: Muster16UpdateComponent;
    let fixture: ComponentFixture<Muster16UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let muster16Service: Muster16Service;
    let m16StatusService: M16StatusService;
    let muster16VerService: Muster16VerService;
    let lieferungService: LieferungService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(Muster16UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      muster16Service = TestBed.inject(Muster16Service);
      m16StatusService = TestBed.inject(M16StatusService);
      muster16VerService = TestBed.inject(Muster16VerService);
      lieferungService = TestBed.inject(LieferungService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call m16Status query and add missing value', () => {
        const muster16: IMuster16 = { id: 456 };
        const m16Status: IM16Status = { id: 35087 };
        muster16.m16Status = m16Status;

        const m16StatusCollection: IM16Status[] = [{ id: 92944 }];
        jest.spyOn(m16StatusService, 'query').mockReturnValue(of(new HttpResponse({ body: m16StatusCollection })));
        const expectedCollection: IM16Status[] = [m16Status, ...m16StatusCollection];
        jest.spyOn(m16StatusService, 'addM16StatusToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ muster16 });
        comp.ngOnInit();

        expect(m16StatusService.query).toHaveBeenCalled();
        expect(m16StatusService.addM16StatusToCollectionIfMissing).toHaveBeenCalledWith(m16StatusCollection, m16Status);
        expect(comp.m16StatusesCollection).toEqual(expectedCollection);
      });

      it('Should call mRezeptId query and add missing value', () => {
        const muster16: IMuster16 = { id: 456 };
        const mRezeptId: IMuster16Ver = { id: 7079 };
        muster16.mRezeptId = mRezeptId;

        const mRezeptIdCollection: IMuster16Ver[] = [{ id: 7414 }];
        jest.spyOn(muster16VerService, 'query').mockReturnValue(of(new HttpResponse({ body: mRezeptIdCollection })));
        const expectedCollection: IMuster16Ver[] = [mRezeptId, ...mRezeptIdCollection];
        jest.spyOn(muster16VerService, 'addMuster16VerToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ muster16 });
        comp.ngOnInit();

        expect(muster16VerService.query).toHaveBeenCalled();
        expect(muster16VerService.addMuster16VerToCollectionIfMissing).toHaveBeenCalledWith(mRezeptIdCollection, mRezeptId);
        expect(comp.mRezeptIdsCollection).toEqual(expectedCollection);
      });

      it('Should call Lieferung query and add missing value', () => {
        const muster16: IMuster16 = { id: 456 };
        const lieferungId: ILieferung = { id: 78136 };
        muster16.lieferungId = lieferungId;

        const lieferungCollection: ILieferung[] = [{ id: 6491 }];
        jest.spyOn(lieferungService, 'query').mockReturnValue(of(new HttpResponse({ body: lieferungCollection })));
        const additionalLieferungs = [lieferungId];
        const expectedCollection: ILieferung[] = [...additionalLieferungs, ...lieferungCollection];
        jest.spyOn(lieferungService, 'addLieferungToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ muster16 });
        comp.ngOnInit();

        expect(lieferungService.query).toHaveBeenCalled();
        expect(lieferungService.addLieferungToCollectionIfMissing).toHaveBeenCalledWith(lieferungCollection, ...additionalLieferungs);
        expect(comp.lieferungsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const muster16: IMuster16 = { id: 456 };
        const m16Status: IM16Status = { id: 87596 };
        muster16.m16Status = m16Status;
        const mRezeptId: IMuster16Ver = { id: 29258 };
        muster16.mRezeptId = mRezeptId;
        const lieferungId: ILieferung = { id: 56397 };
        muster16.lieferungId = lieferungId;

        activatedRoute.data = of({ muster16 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(muster16));
        expect(comp.m16StatusesCollection).toContain(m16Status);
        expect(comp.mRezeptIdsCollection).toContain(mRezeptId);
        expect(comp.lieferungsSharedCollection).toContain(lieferungId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16>>();
        const muster16 = { id: 123 };
        jest.spyOn(muster16Service, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(muster16Service.update).toHaveBeenCalledWith(muster16);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16>>();
        const muster16 = new Muster16();
        jest.spyOn(muster16Service, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16 }));
        saveSubject.complete();

        // THEN
        expect(muster16Service.create).toHaveBeenCalledWith(muster16);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16>>();
        const muster16 = { id: 123 };
        jest.spyOn(muster16Service, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(muster16Service.update).toHaveBeenCalledWith(muster16);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackM16StatusById', () => {
        it('Should return tracked M16Status primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackM16StatusById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackMuster16VerById', () => {
        it('Should return tracked Muster16Ver primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMuster16VerById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackLieferungById', () => {
        it('Should return tracked Lieferung primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackLieferungById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
