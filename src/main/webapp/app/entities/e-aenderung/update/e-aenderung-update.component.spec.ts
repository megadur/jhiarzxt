jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EAenderungService } from '../service/e-aenderung.service';
import { IEAenderung, EAenderung } from '../e-aenderung.model';
import { IERezept } from 'app/entities/e-rezept/e-rezept.model';
import { ERezeptService } from 'app/entities/e-rezept/service/e-rezept.service';

import { EAenderungUpdateComponent } from './e-aenderung-update.component';

describe('Component Tests', () => {
  describe('EAenderung Management Update Component', () => {
    let comp: EAenderungUpdateComponent;
    let fixture: ComponentFixture<EAenderungUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let eAenderungService: EAenderungService;
    let eRezeptService: ERezeptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EAenderungUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EAenderungUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EAenderungUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      eAenderungService = TestBed.inject(EAenderungService);
      eRezeptService = TestBed.inject(ERezeptService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ERezept query and add missing value', () => {
        const eAenderung: IEAenderung = { id: 456 };
        const eRezept: IERezept = { id: 63619 };
        eAenderung.eRezept = eRezept;

        const eRezeptCollection: IERezept[] = [{ id: 21381 }];
        jest.spyOn(eRezeptService, 'query').mockReturnValue(of(new HttpResponse({ body: eRezeptCollection })));
        const additionalERezepts = [eRezept];
        const expectedCollection: IERezept[] = [...additionalERezepts, ...eRezeptCollection];
        jest.spyOn(eRezeptService, 'addERezeptToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ eAenderung });
        comp.ngOnInit();

        expect(eRezeptService.query).toHaveBeenCalled();
        expect(eRezeptService.addERezeptToCollectionIfMissing).toHaveBeenCalledWith(eRezeptCollection, ...additionalERezepts);
        expect(comp.eRezeptsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const eAenderung: IEAenderung = { id: 456 };
        const eRezept: IERezept = { id: 57918 };
        eAenderung.eRezept = eRezept;

        activatedRoute.data = of({ eAenderung });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(eAenderung));
        expect(comp.eRezeptsSharedCollection).toContain(eRezept);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EAenderung>>();
        const eAenderung = { id: 123 };
        jest.spyOn(eAenderungService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eAenderung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eAenderung }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(eAenderungService.update).toHaveBeenCalledWith(eAenderung);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EAenderung>>();
        const eAenderung = new EAenderung();
        jest.spyOn(eAenderungService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eAenderung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eAenderung }));
        saveSubject.complete();

        // THEN
        expect(eAenderungService.create).toHaveBeenCalledWith(eAenderung);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EAenderung>>();
        const eAenderung = { id: 123 };
        jest.spyOn(eAenderungService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eAenderung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(eAenderungService.update).toHaveBeenCalledWith(eAenderung);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackERezeptById', () => {
        it('Should return tracked ERezept primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackERezeptById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
