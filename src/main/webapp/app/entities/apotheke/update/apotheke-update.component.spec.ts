jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ApothekeService } from '../service/apotheke.service';
import { IApotheke, Apotheke } from '../apotheke.model';
import { IRechenzentrum } from 'app/entities/rechenzentrum/rechenzentrum.model';
import { RechenzentrumService } from 'app/entities/rechenzentrum/service/rechenzentrum.service';

import { ApothekeUpdateComponent } from './apotheke-update.component';

describe('Component Tests', () => {
  describe('Apotheke Management Update Component', () => {
    let comp: ApothekeUpdateComponent;
    let fixture: ComponentFixture<ApothekeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let apothekeService: ApothekeService;
    let rechenzentrumService: RechenzentrumService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ApothekeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ApothekeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApothekeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      apothekeService = TestBed.inject(ApothekeService);
      rechenzentrumService = TestBed.inject(RechenzentrumService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Rechenzentrum query and add missing value', () => {
        const apotheke: IApotheke = { id: 456 };
        const rechenzentrums: IRechenzentrum[] = [{ id: 33523 }];
        apotheke.rechenzentrums = rechenzentrums;

        const rechenzentrumCollection: IRechenzentrum[] = [{ id: 9905 }];
        jest.spyOn(rechenzentrumService, 'query').mockReturnValue(of(new HttpResponse({ body: rechenzentrumCollection })));
        const additionalRechenzentrums = [...rechenzentrums];
        const expectedCollection: IRechenzentrum[] = [...additionalRechenzentrums, ...rechenzentrumCollection];
        jest.spyOn(rechenzentrumService, 'addRechenzentrumToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ apotheke });
        comp.ngOnInit();

        expect(rechenzentrumService.query).toHaveBeenCalled();
        expect(rechenzentrumService.addRechenzentrumToCollectionIfMissing).toHaveBeenCalledWith(
          rechenzentrumCollection,
          ...additionalRechenzentrums
        );
        expect(comp.rechenzentrumsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const apotheke: IApotheke = { id: 456 };
        const rechenzentrums: IRechenzentrum = { id: 63818 };
        apotheke.rechenzentrums = [rechenzentrums];

        activatedRoute.data = of({ apotheke });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(apotheke));
        expect(comp.rechenzentrumsSharedCollection).toContain(rechenzentrums);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Apotheke>>();
        const apotheke = { id: 123 };
        jest.spyOn(apothekeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ apotheke });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: apotheke }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(apothekeService.update).toHaveBeenCalledWith(apotheke);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Apotheke>>();
        const apotheke = new Apotheke();
        jest.spyOn(apothekeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ apotheke });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: apotheke }));
        saveSubject.complete();

        // THEN
        expect(apothekeService.create).toHaveBeenCalledWith(apotheke);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Apotheke>>();
        const apotheke = { id: 123 };
        jest.spyOn(apothekeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ apotheke });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(apothekeService.update).toHaveBeenCalledWith(apotheke);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackRechenzentrumById', () => {
        it('Should return tracked Rechenzentrum primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRechenzentrumById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedRechenzentrum', () => {
        it('Should return option if no Rechenzentrum is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedRechenzentrum(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Rechenzentrum for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedRechenzentrum(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Rechenzentrum is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedRechenzentrum(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
