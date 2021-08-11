jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PChargeService } from '../service/p-charge.service';
import { IPCharge, PCharge } from '../p-charge.model';
import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';
import { PRezeptService } from 'app/entities/p-rezept/service/p-rezept.service';

import { PChargeUpdateComponent } from './p-charge-update.component';

describe('Component Tests', () => {
  describe('PCharge Management Update Component', () => {
    let comp: PChargeUpdateComponent;
    let fixture: ComponentFixture<PChargeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pChargeService: PChargeService;
    let pRezeptService: PRezeptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PChargeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PChargeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PChargeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pChargeService = TestBed.inject(PChargeService);
      pRezeptService = TestBed.inject(PRezeptService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PRezept query and add missing value', () => {
        const pCharge: IPCharge = { id: 456 };
        const pRezept: IPRezept = { id: 96762 };
        pCharge.pRezept = pRezept;

        const pRezeptCollection: IPRezept[] = [{ id: 61017 }];
        jest.spyOn(pRezeptService, 'query').mockReturnValue(of(new HttpResponse({ body: pRezeptCollection })));
        const additionalPRezepts = [pRezept];
        const expectedCollection: IPRezept[] = [...additionalPRezepts, ...pRezeptCollection];
        jest.spyOn(pRezeptService, 'addPRezeptToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pCharge });
        comp.ngOnInit();

        expect(pRezeptService.query).toHaveBeenCalled();
        expect(pRezeptService.addPRezeptToCollectionIfMissing).toHaveBeenCalledWith(pRezeptCollection, ...additionalPRezepts);
        expect(comp.pRezeptsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const pCharge: IPCharge = { id: 456 };
        const pRezept: IPRezept = { id: 94147 };
        pCharge.pRezept = pRezept;

        activatedRoute.data = of({ pCharge });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pCharge));
        expect(comp.pRezeptsSharedCollection).toContain(pRezept);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PCharge>>();
        const pCharge = { id: 123 };
        jest.spyOn(pChargeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pCharge });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pCharge }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pChargeService.update).toHaveBeenCalledWith(pCharge);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PCharge>>();
        const pCharge = new PCharge();
        jest.spyOn(pChargeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pCharge });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pCharge }));
        saveSubject.complete();

        // THEN
        expect(pChargeService.create).toHaveBeenCalledWith(pCharge);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PCharge>>();
        const pCharge = { id: 123 };
        jest.spyOn(pChargeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pCharge });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pChargeService.update).toHaveBeenCalledWith(pCharge);
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
