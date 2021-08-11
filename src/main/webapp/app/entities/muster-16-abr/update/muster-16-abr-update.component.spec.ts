jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { Muster16AbrService } from '../service/muster-16-abr.service';
import { IMuster16Abr, Muster16Abr } from '../muster-16-abr.model';

import { Muster16AbrUpdateComponent } from './muster-16-abr-update.component';

describe('Component Tests', () => {
  describe('Muster16Abr Management Update Component', () => {
    let comp: Muster16AbrUpdateComponent;
    let fixture: ComponentFixture<Muster16AbrUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let muster16AbrService: Muster16AbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16AbrUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(Muster16AbrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16AbrUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      muster16AbrService = TestBed.inject(Muster16AbrService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const muster16Abr: IMuster16Abr = { id: 456 };

        activatedRoute.data = of({ muster16Abr });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(muster16Abr));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Abr>>();
        const muster16Abr = { id: 123 };
        jest.spyOn(muster16AbrService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Abr });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16Abr }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(muster16AbrService.update).toHaveBeenCalledWith(muster16Abr);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Abr>>();
        const muster16Abr = new Muster16Abr();
        jest.spyOn(muster16AbrService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Abr });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muster16Abr }));
        saveSubject.complete();

        // THEN
        expect(muster16AbrService.create).toHaveBeenCalledWith(muster16Abr);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Muster16Abr>>();
        const muster16Abr = { id: 123 };
        jest.spyOn(muster16AbrService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ muster16Abr });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(muster16AbrService.update).toHaveBeenCalledWith(muster16Abr);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
