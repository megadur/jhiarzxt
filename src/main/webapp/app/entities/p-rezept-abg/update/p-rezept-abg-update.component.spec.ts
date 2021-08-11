jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PRezeptAbgService } from '../service/p-rezept-abg.service';
import { IPRezeptAbg, PRezeptAbg } from '../p-rezept-abg.model';
import { IPRezeptAbr } from 'app/entities/p-rezept-abr/p-rezept-abr.model';
import { PRezeptAbrService } from 'app/entities/p-rezept-abr/service/p-rezept-abr.service';

import { PRezeptAbgUpdateComponent } from './p-rezept-abg-update.component';

describe('Component Tests', () => {
  describe('PRezeptAbg Management Update Component', () => {
    let comp: PRezeptAbgUpdateComponent;
    let fixture: ComponentFixture<PRezeptAbgUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pRezeptAbgService: PRezeptAbgService;
    let pRezeptAbrService: PRezeptAbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptAbgUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PRezeptAbgUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PRezeptAbgUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pRezeptAbgService = TestBed.inject(PRezeptAbgService);
      pRezeptAbrService = TestBed.inject(PRezeptAbrService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call pRezeptId query and add missing value', () => {
        const pRezeptAbg: IPRezeptAbg = { id: 456 };
        const pRezeptId: IPRezeptAbr = { id: 84465 };
        pRezeptAbg.pRezeptId = pRezeptId;

        const pRezeptIdCollection: IPRezeptAbr[] = [{ id: 77802 }];
        jest.spyOn(pRezeptAbrService, 'query').mockReturnValue(of(new HttpResponse({ body: pRezeptIdCollection })));
        const expectedCollection: IPRezeptAbr[] = [pRezeptId, ...pRezeptIdCollection];
        jest.spyOn(pRezeptAbrService, 'addPRezeptAbrToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pRezeptAbg });
        comp.ngOnInit();

        expect(pRezeptAbrService.query).toHaveBeenCalled();
        expect(pRezeptAbrService.addPRezeptAbrToCollectionIfMissing).toHaveBeenCalledWith(pRezeptIdCollection, pRezeptId);
        expect(comp.pRezeptIdsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const pRezeptAbg: IPRezeptAbg = { id: 456 };
        const pRezeptId: IPRezeptAbr = { id: 63159 };
        pRezeptAbg.pRezeptId = pRezeptId;

        activatedRoute.data = of({ pRezeptAbg });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pRezeptAbg));
        expect(comp.pRezeptIdsCollection).toContain(pRezeptId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptAbg>>();
        const pRezeptAbg = { id: 123 };
        jest.spyOn(pRezeptAbgService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptAbg });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezeptAbg }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pRezeptAbgService.update).toHaveBeenCalledWith(pRezeptAbg);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptAbg>>();
        const pRezeptAbg = new PRezeptAbg();
        jest.spyOn(pRezeptAbgService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptAbg });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezeptAbg }));
        saveSubject.complete();

        // THEN
        expect(pRezeptAbgService.create).toHaveBeenCalledWith(pRezeptAbg);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptAbg>>();
        const pRezeptAbg = { id: 123 };
        jest.spyOn(pRezeptAbgService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptAbg });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pRezeptAbgService.update).toHaveBeenCalledWith(pRezeptAbg);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPRezeptAbrById', () => {
        it('Should return tracked PRezeptAbr primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPRezeptAbrById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
