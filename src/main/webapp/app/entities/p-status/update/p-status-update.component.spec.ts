jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PStatusService } from '../service/p-status.service';
import { IPStatus, PStatus } from '../p-status.model';

import { PStatusUpdateComponent } from './p-status-update.component';

describe('Component Tests', () => {
  describe('PStatus Management Update Component', () => {
    let comp: PStatusUpdateComponent;
    let fixture: ComponentFixture<PStatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pStatusService: PStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PStatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PStatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pStatusService = TestBed.inject(PStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const pStatus: IPStatus = { id: 456 };

        activatedRoute.data = of({ pStatus });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pStatus));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PStatus>>();
        const pStatus = { id: 123 };
        jest.spyOn(pStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pStatus }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pStatusService.update).toHaveBeenCalledWith(pStatus);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PStatus>>();
        const pStatus = new PStatus();
        jest.spyOn(pStatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pStatus }));
        saveSubject.complete();

        // THEN
        expect(pStatusService.create).toHaveBeenCalledWith(pStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PStatus>>();
        const pStatus = { id: 123 };
        jest.spyOn(pStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pStatusService.update).toHaveBeenCalledWith(pStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
