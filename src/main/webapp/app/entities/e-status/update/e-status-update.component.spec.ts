jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EStatusService } from '../service/e-status.service';
import { IEStatus, EStatus } from '../e-status.model';

import { EStatusUpdateComponent } from './e-status-update.component';

describe('Component Tests', () => {
  describe('EStatus Management Update Component', () => {
    let comp: EStatusUpdateComponent;
    let fixture: ComponentFixture<EStatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let eStatusService: EStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EStatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EStatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      eStatusService = TestBed.inject(EStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const eStatus: IEStatus = { id: 456 };

        activatedRoute.data = of({ eStatus });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(eStatus));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EStatus>>();
        const eStatus = { id: 123 };
        jest.spyOn(eStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eStatus }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(eStatusService.update).toHaveBeenCalledWith(eStatus);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EStatus>>();
        const eStatus = new EStatus();
        jest.spyOn(eStatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eStatus }));
        saveSubject.complete();

        // THEN
        expect(eStatusService.create).toHaveBeenCalledWith(eStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EStatus>>();
        const eStatus = { id: 123 };
        jest.spyOn(eStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(eStatusService.update).toHaveBeenCalledWith(eStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
