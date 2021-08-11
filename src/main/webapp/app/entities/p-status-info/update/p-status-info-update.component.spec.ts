jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PStatusInfoService } from '../service/p-status-info.service';
import { IPStatusInfo, PStatusInfo } from '../p-status-info.model';
import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';
import { PRezeptService } from 'app/entities/p-rezept/service/p-rezept.service';

import { PStatusInfoUpdateComponent } from './p-status-info-update.component';

describe('Component Tests', () => {
  describe('PStatusInfo Management Update Component', () => {
    let comp: PStatusInfoUpdateComponent;
    let fixture: ComponentFixture<PStatusInfoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pStatusInfoService: PStatusInfoService;
    let pRezeptService: PRezeptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PStatusInfoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PStatusInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PStatusInfoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pStatusInfoService = TestBed.inject(PStatusInfoService);
      pRezeptService = TestBed.inject(PRezeptService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PRezept query and add missing value', () => {
        const pStatusInfo: IPStatusInfo = { id: 456 };
        const pStatusInfoId: IPRezept = { id: 94728 };
        pStatusInfo.pStatusInfoId = pStatusInfoId;

        const pRezeptCollection: IPRezept[] = [{ id: 96639 }];
        jest.spyOn(pRezeptService, 'query').mockReturnValue(of(new HttpResponse({ body: pRezeptCollection })));
        const additionalPRezepts = [pStatusInfoId];
        const expectedCollection: IPRezept[] = [...additionalPRezepts, ...pRezeptCollection];
        jest.spyOn(pRezeptService, 'addPRezeptToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pStatusInfo });
        comp.ngOnInit();

        expect(pRezeptService.query).toHaveBeenCalled();
        expect(pRezeptService.addPRezeptToCollectionIfMissing).toHaveBeenCalledWith(pRezeptCollection, ...additionalPRezepts);
        expect(comp.pRezeptsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const pStatusInfo: IPStatusInfo = { id: 456 };
        const pStatusInfoId: IPRezept = { id: 93670 };
        pStatusInfo.pStatusInfoId = pStatusInfoId;

        activatedRoute.data = of({ pStatusInfo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pStatusInfo));
        expect(comp.pRezeptsSharedCollection).toContain(pStatusInfoId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PStatusInfo>>();
        const pStatusInfo = { id: 123 };
        jest.spyOn(pStatusInfoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pStatusInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pStatusInfo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pStatusInfoService.update).toHaveBeenCalledWith(pStatusInfo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PStatusInfo>>();
        const pStatusInfo = new PStatusInfo();
        jest.spyOn(pStatusInfoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pStatusInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pStatusInfo }));
        saveSubject.complete();

        // THEN
        expect(pStatusInfoService.create).toHaveBeenCalledWith(pStatusInfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PStatusInfo>>();
        const pStatusInfo = { id: 123 };
        jest.spyOn(pStatusInfoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pStatusInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pStatusInfoService.update).toHaveBeenCalledWith(pStatusInfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPRezeptById', () => {
        it('Should return tracked PRezept primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPRezeptById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
