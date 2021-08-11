jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { Muster16AbgService } from '../service/muster-16-abg.service';
import { IMuster16Abg, Muster16Abg } from '../muster-16-abg.model';
import { IMuster16Abr } from 'app/entities/muster-16-abr/muster-16-abr.model';
import { Muster16AbrService } from 'app/entities/muster-16-abr/service/muster-16-abr.service';

import { Muster16AbgUpdateComponent } from './muster-16-abg-update.component';

describe('Component Tests', () => {
  describe('Muster16Abg Management Update Component', () => {
    let comp: Muster16AbgUpdateComponent;
    let fixture: ComponentFixture<Muster16AbgUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let muster16AbgService: Muster16AbgService;
    let muster16AbrService: Muster16AbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16AbgUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(Muster16AbgUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16AbgUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      muster16AbgService = TestBed.inject(Muster16AbgService);
      muster16AbrService = TestBed.inject(Muster16AbrService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call mRezeptId query and add missing value', () => {
        const muster16Abg: IMuster16Abg = { id: 456 };
        const mRezeptId: IMuster16Abr = { id: 31124 };
        muster16Abg.mRezeptId = mRezeptId;

        const mRezeptIdCollection: IMuster16Abr[] = [{ id: 10563 }];
        jest.spyOn(muster16AbrService, 'query').mockReturnValue(of(new HttpResponse({ body: mRezeptIdCollection })));
        const expectedCollection: IMuster16Abr[] = [mRezeptId, ...mRezeptIdCollection];
        jest.spyOn(muster16AbrService, 'addMuster16AbrToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ muster16Abg });
        comp.ngOnInit();

        expect(muster16AbrService.query).toHaveBeenCalled();
        expect(muster16AbrService.addMuster16AbrToCollectionIfMissing).toHaveBeenCalledWith(mRezeptIdCollection, mRezeptId);
        expect(comp.mRezeptIdsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const muster16Abg: IMuster16Abg = { id: 456 };
        const mRezeptId: IMuster16Abr = { id: 25109 };
        muster16Abg.mRezeptId = mRezeptId;

        activatedRoute.data = of({ muster16Abg });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(muster16Abg));
        expect(comp.mRezeptIdsCollection).toContain(mRezeptId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Abg>>();
        const muster16Abg = { id: 123 };
        jest.spyOn(muster16AbgService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Abg });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16Abg }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(muster16AbgService.update).toHaveBeenCalledWith(muster16Abg);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Abg>>();
        const muster16Abg = new Muster16Abg();
        jest.spyOn(muster16AbgService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Abg });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16Abg }));
        saveSubject.complete();

        // THEN
        expect(muster16AbgService.create).toHaveBeenCalledWith(muster16Abg);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Abg>>();
        const muster16Abg = { id: 123 };
        jest.spyOn(muster16AbgService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Abg });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(muster16AbgService.update).toHaveBeenCalledWith(muster16Abg);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMuster16AbrById', () => {
        it('Should return tracked Muster16Abr primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMuster16AbrById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
