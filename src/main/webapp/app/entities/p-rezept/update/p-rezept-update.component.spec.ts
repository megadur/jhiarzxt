jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PRezeptService } from '../service/p-rezept.service';
import { IPRezept, PRezept } from '../p-rezept.model';
import { IPStatus } from 'app/entities/p-status/p-status.model';
import { PStatusService } from 'app/entities/p-status/service/p-status.service';
import { IPRezeptVer } from 'app/entities/p-rezept-ver/p-rezept-ver.model';
import { PRezeptVerService } from 'app/entities/p-rezept-ver/service/p-rezept-ver.service';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';
import { LieferungService } from 'app/entities/lieferung/service/lieferung.service';

import { PRezeptUpdateComponent } from './p-rezept-update.component';

describe('Component Tests', () => {
  describe('PRezept Management Update Component', () => {
    let comp: PRezeptUpdateComponent;
    let fixture: ComponentFixture<PRezeptUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pRezeptService: PRezeptService;
    let pStatusService: PStatusService;
    let pRezeptVerService: PRezeptVerService;
    let lieferungService: LieferungService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PRezeptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PRezeptUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pRezeptService = TestBed.inject(PRezeptService);
      pStatusService = TestBed.inject(PStatusService);
      pRezeptVerService = TestBed.inject(PRezeptVerService);
      lieferungService = TestBed.inject(LieferungService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call pStatus query and add missing value', () => {
        const pRezept: IPRezept = { id: 456 };
        const pStatus: IPStatus = { id: 31003 };
        pRezept.pStatus = pStatus;

        const pStatusCollection: IPStatus[] = [{ id: 25046 }];
        jest.spyOn(pStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: pStatusCollection })));
        const expectedCollection: IPStatus[] = [pStatus, ...pStatusCollection];
        jest.spyOn(pStatusService, 'addPStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pRezept });
        comp.ngOnInit();

        expect(pStatusService.query).toHaveBeenCalled();
        expect(pStatusService.addPStatusToCollectionIfMissing).toHaveBeenCalledWith(pStatusCollection, pStatus);
        expect(comp.pStatusesCollection).toEqual(expectedCollection);
      });

      it('Should call pRezeptId query and add missing value', () => {
        const pRezept: IPRezept = { id: 456 };
        const pRezeptId: IPRezeptVer = { id: 41987 };
        pRezept.pRezeptId = pRezeptId;

        const pRezeptIdCollection: IPRezeptVer[] = [{ id: 86212 }];
        jest.spyOn(pRezeptVerService, 'query').mockReturnValue(of(new HttpResponse({ body: pRezeptIdCollection })));
        const expectedCollection: IPRezeptVer[] = [pRezeptId, ...pRezeptIdCollection];
        jest.spyOn(pRezeptVerService, 'addPRezeptVerToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pRezept });
        comp.ngOnInit();

        expect(pRezeptVerService.query).toHaveBeenCalled();
        expect(pRezeptVerService.addPRezeptVerToCollectionIfMissing).toHaveBeenCalledWith(pRezeptIdCollection, pRezeptId);
        expect(comp.pRezeptIdsCollection).toEqual(expectedCollection);
      });

      it('Should call Lieferung query and add missing value', () => {
        const pRezept: IPRezept = { id: 456 };
        const lieferungId: ILieferung = { id: 50235 };
        pRezept.lieferungId = lieferungId;

        const lieferungCollection: ILieferung[] = [{ id: 64774 }];
        jest.spyOn(lieferungService, 'query').mockReturnValue(of(new HttpResponse({ body: lieferungCollection })));
        const additionalLieferungs = [lieferungId];
        const expectedCollection: ILieferung[] = [...additionalLieferungs, ...lieferungCollection];
        jest.spyOn(lieferungService, 'addLieferungToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pRezept });
        comp.ngOnInit();

        expect(lieferungService.query).toHaveBeenCalled();
        expect(lieferungService.addLieferungToCollectionIfMissing).toHaveBeenCalledWith(lieferungCollection, ...additionalLieferungs);
        expect(comp.lieferungsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const pRezept: IPRezept = { id: 456 };
        const pStatus: IPStatus = { id: 90954 };
        pRezept.pStatus = pStatus;
        const pRezeptId: IPRezeptVer = { id: 54678 };
        pRezept.pRezeptId = pRezeptId;
        const lieferungId: ILieferung = { id: 65373 };
        pRezept.lieferungId = lieferungId;

        activatedRoute.data = of({ pRezept });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pRezept));
        expect(comp.pStatusesCollection).toContain(pStatus);
        expect(comp.pRezeptIdsCollection).toContain(pRezeptId);
        expect(comp.lieferungsSharedCollection).toContain(lieferungId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezept>>();
        const pRezept = { id: 123 };
        jest.spyOn(pRezeptService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezept }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pRezeptService.update).toHaveBeenCalledWith(pRezept);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezept>>();
        const pRezept = new PRezept();
        jest.spyOn(pRezeptService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezept }));
        saveSubject.complete();

        // THEN
        expect(pRezeptService.create).toHaveBeenCalledWith(pRezept);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezept>>();
        const pRezept = { id: 123 };
        jest.spyOn(pRezeptService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pRezeptService.update).toHaveBeenCalledWith(pRezept);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPStatusById', () => {
        it('Should return tracked PStatus primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPStatusById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackPRezeptVerById', () => {
        it('Should return tracked PRezeptVer primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPRezeptVerById(0, entity);
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
