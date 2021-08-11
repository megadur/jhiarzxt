jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PRezeptAbrService } from '../service/p-rezept-abr.service';
import { IPRezeptAbr, PRezeptAbr } from '../p-rezept-abr.model';

import { PRezeptAbrUpdateComponent } from './p-rezept-abr-update.component';

describe('Component Tests', () => {
  describe('PRezeptAbr Management Update Component', () => {
    let comp: PRezeptAbrUpdateComponent;
    let fixture: ComponentFixture<PRezeptAbrUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pRezeptAbrService: PRezeptAbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptAbrUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PRezeptAbrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PRezeptAbrUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pRezeptAbrService = TestBed.inject(PRezeptAbrService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const pRezeptAbr: IPRezeptAbr = { id: 456 };

        activatedRoute.data = of({ pRezeptAbr });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pRezeptAbr));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptAbr>>();
        const pRezeptAbr = { id: 123 };
        jest.spyOn(pRezeptAbrService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptAbr });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezeptAbr }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pRezeptAbrService.update).toHaveBeenCalledWith(pRezeptAbr);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptAbr>>();
        const pRezeptAbr = new PRezeptAbr();
        jest.spyOn(pRezeptAbrService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptAbr });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezeptAbr }));
        saveSubject.complete();

        // THEN
        expect(pRezeptAbrService.create).toHaveBeenCalledWith(pRezeptAbr);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptAbr>>();
        const pRezeptAbr = { id: 123 };
        jest.spyOn(pRezeptAbrService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptAbr });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pRezeptAbrService.update).toHaveBeenCalledWith(pRezeptAbr);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
