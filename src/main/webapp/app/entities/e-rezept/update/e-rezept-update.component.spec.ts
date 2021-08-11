jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ERezeptService } from '../service/e-rezept.service';
import { IERezept, ERezept } from '../e-rezept.model';
import { IEStatus } from 'app/entities/e-status/e-status.model';
import { EStatusService } from 'app/entities/e-status/service/e-status.service';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';
import { LieferungService } from 'app/entities/lieferung/service/lieferung.service';

import { ERezeptUpdateComponent } from './e-rezept-update.component';

describe('Component Tests', () => {
  describe('ERezept Management Update Component', () => {
    let comp: ERezeptUpdateComponent;
    let fixture: ComponentFixture<ERezeptUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let eRezeptService: ERezeptService;
    let eStatusService: EStatusService;
    let lieferungService: LieferungService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ERezeptUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ERezeptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ERezeptUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      eRezeptService = TestBed.inject(ERezeptService);
      eStatusService = TestBed.inject(EStatusService);
      lieferungService = TestBed.inject(LieferungService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call eStatus query and add missing value', () => {
        const eRezept: IERezept = { id: 456 };
        const eStatus: IEStatus = { id: 18693 };
        eRezept.eStatus = eStatus;

        const eStatusCollection: IEStatus[] = [{ id: 56739 }];
        jest.spyOn(eStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: eStatusCollection })));
        const expectedCollection: IEStatus[] = [eStatus, ...eStatusCollection];
        jest.spyOn(eStatusService, 'addEStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ eRezept });
        comp.ngOnInit();

        expect(eStatusService.query).toHaveBeenCalled();
        expect(eStatusService.addEStatusToCollectionIfMissing).toHaveBeenCalledWith(eStatusCollection, eStatus);
        expect(comp.eStatusesCollection).toEqual(expectedCollection);
      });

      it('Should call Lieferung query and add missing value', () => {
        const eRezept: IERezept = { id: 456 };
        const lieferungId: ILieferung = { id: 14798 };
        eRezept.lieferungId = lieferungId;

        const lieferungCollection: ILieferung[] = [{ id: 89279 }];
        jest.spyOn(lieferungService, 'query').mockReturnValue(of(new HttpResponse({ body: lieferungCollection })));
        const additionalLieferungs = [lieferungId];
        const expectedCollection: ILieferung[] = [...additionalLieferungs, ...lieferungCollection];
        jest.spyOn(lieferungService, 'addLieferungToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ eRezept });
        comp.ngOnInit();

        expect(lieferungService.query).toHaveBeenCalled();
        expect(lieferungService.addLieferungToCollectionIfMissing).toHaveBeenCalledWith(lieferungCollection, ...additionalLieferungs);
        expect(comp.lieferungsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const eRezept: IERezept = { id: 456 };
        const eStatus: IEStatus = { id: 45377 };
        eRezept.eStatus = eStatus;
        const lieferungId: ILieferung = { id: 5381 };
        eRezept.lieferungId = lieferungId;

        activatedRoute.data = of({ eRezept });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(eRezept));
        expect(comp.eStatusesCollection).toContain(eStatus);
        expect(comp.lieferungsSharedCollection).toContain(lieferungId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ERezept>>();
        const eRezept = { id: 123 };
        jest.spyOn(eRezeptService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eRezept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eRezept }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(eRezeptService.update).toHaveBeenCalledWith(eRezept);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ERezept>>();
        const eRezept = new ERezept();
        jest.spyOn(eRezeptService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eRezept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eRezept }));
        saveSubject.complete();

        // THEN
        expect(eRezeptService.create).toHaveBeenCalledWith(eRezept);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ERezept>>();
        const eRezept = { id: 123 };
        jest.spyOn(eRezeptService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eRezept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(eRezeptService.update).toHaveBeenCalledWith(eRezept);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEStatusById', () => {
        it('Should return tracked EStatus primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEStatusById(0, entity);
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
