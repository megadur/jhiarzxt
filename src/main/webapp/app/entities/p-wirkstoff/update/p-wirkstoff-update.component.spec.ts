jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PWirkstoffService } from '../service/p-wirkstoff.service';
import { IPWirkstoff, PWirkstoff } from '../p-wirkstoff.model';
import { IPCharge } from 'app/entities/p-charge/p-charge.model';
import { PChargeService } from 'app/entities/p-charge/service/p-charge.service';

import { PWirkstoffUpdateComponent } from './p-wirkstoff-update.component';

describe('Component Tests', () => {
  describe('PWirkstoff Management Update Component', () => {
    let comp: PWirkstoffUpdateComponent;
    let fixture: ComponentFixture<PWirkstoffUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pWirkstoffService: PWirkstoffService;
    let pChargeService: PChargeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PWirkstoffUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PWirkstoffUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PWirkstoffUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pWirkstoffService = TestBed.inject(PWirkstoffService);
      pChargeService = TestBed.inject(PChargeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PCharge query and add missing value', () => {
        const pWirkstoff: IPWirkstoff = { id: 456 };
        const pCharge: IPCharge = { id: 72975 };
        pWirkstoff.pCharge = pCharge;

        const pChargeCollection: IPCharge[] = [{ id: 16844 }];
        jest.spyOn(pChargeService, 'query').mockReturnValue(of(new HttpResponse({ body: pChargeCollection })));
        const additionalPCharges = [pCharge];
        const expectedCollection: IPCharge[] = [...additionalPCharges, ...pChargeCollection];
        jest.spyOn(pChargeService, 'addPChargeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pWirkstoff });
        comp.ngOnInit();

        expect(pChargeService.query).toHaveBeenCalled();
        expect(pChargeService.addPChargeToCollectionIfMissing).toHaveBeenCalledWith(pChargeCollection, ...additionalPCharges);
        expect(comp.pChargesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const pWirkstoff: IPWirkstoff = { id: 456 };
        const pCharge: IPCharge = { id: 88071 };
        pWirkstoff.pCharge = pCharge;

        activatedRoute.data = of({ pWirkstoff });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pWirkstoff));
        expect(comp.pChargesSharedCollection).toContain(pCharge);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PWirkstoff>>();
        const pWirkstoff = { id: 123 };
        jest.spyOn(pWirkstoffService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pWirkstoff });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pWirkstoff }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pWirkstoffService.update).toHaveBeenCalledWith(pWirkstoff);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PWirkstoff>>();
        const pWirkstoff = new PWirkstoff();
        jest.spyOn(pWirkstoffService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pWirkstoff });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pWirkstoff }));
        saveSubject.complete();

        // THEN
        expect(pWirkstoffService.create).toHaveBeenCalledWith(pWirkstoff);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PWirkstoff>>();
        const pWirkstoff = { id: 123 };
        jest.spyOn(pWirkstoffService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pWirkstoff });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pWirkstoffService.update).toHaveBeenCalledWith(pWirkstoff);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPChargeById', () => {
        it('Should return tracked PCharge primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPChargeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
