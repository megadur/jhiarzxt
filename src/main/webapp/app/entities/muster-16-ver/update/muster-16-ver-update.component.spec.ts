jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { Muster16VerService } from '../service/muster-16-ver.service';
import { IMuster16Ver, Muster16Ver } from '../muster-16-ver.model';
import { IMuster16Abg } from 'app/entities/muster-16-abg/muster-16-abg.model';
import { Muster16AbgService } from 'app/entities/muster-16-abg/service/muster-16-abg.service';

import { Muster16VerUpdateComponent } from './muster-16-ver-update.component';

describe('Component Tests', () => {
  describe('Muster16Ver Management Update Component', () => {
    let comp: Muster16VerUpdateComponent;
    let fixture: ComponentFixture<Muster16VerUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let muster16VerService: Muster16VerService;
    let muster16AbgService: Muster16AbgService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16VerUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(Muster16VerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16VerUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      muster16VerService = TestBed.inject(Muster16VerService);
      muster16AbgService = TestBed.inject(Muster16AbgService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call mRezeptId query and add missing value', () => {
        const muster16Ver: IMuster16Ver = { id: 456 };
        const mRezeptId: IMuster16Abg = { id: 87483 };
        muster16Ver.mRezeptId = mRezeptId;

        const mRezeptIdCollection: IMuster16Abg[] = [{ id: 2293 }];
        jest.spyOn(muster16AbgService, 'query').mockReturnValue(of(new HttpResponse({ body: mRezeptIdCollection })));
        const expectedCollection: IMuster16Abg[] = [mRezeptId, ...mRezeptIdCollection];
        jest.spyOn(muster16AbgService, 'addMuster16AbgToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ muster16Ver });
        comp.ngOnInit();

        expect(muster16AbgService.query).toHaveBeenCalled();
        expect(muster16AbgService.addMuster16AbgToCollectionIfMissing).toHaveBeenCalledWith(mRezeptIdCollection, mRezeptId);
        expect(comp.mRezeptIdsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const muster16Ver: IMuster16Ver = { id: 456 };
        const mRezeptId: IMuster16Abg = { id: 47296 };
        muster16Ver.mRezeptId = mRezeptId;

        activatedRoute.data = of({ muster16Ver });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(muster16Ver));
        expect(comp.mRezeptIdsCollection).toContain(mRezeptId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Ver>>();
        const muster16Ver = { id: 123 };
        jest.spyOn(muster16VerService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Ver });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16Ver }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(muster16VerService.update).toHaveBeenCalledWith(muster16Ver);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Ver>>();
        const muster16Ver = new Muster16Ver();
        jest.spyOn(muster16VerService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Ver });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16Ver }));
        saveSubject.complete();

        // THEN
        expect(muster16VerService.create).toHaveBeenCalledWith(muster16Ver);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Ver>>();
        const muster16Ver = { id: 123 };
        jest.spyOn(muster16VerService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Ver });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(muster16VerService.update).toHaveBeenCalledWith(muster16Ver);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMuster16AbgById', () => {
        it('Should return tracked Muster16Abg primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMuster16AbgById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
