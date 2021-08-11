jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { M16StatusService } from '../service/m-16-status.service';
import { IM16Status, M16Status } from '../m-16-status.model';

import { M16StatusUpdateComponent } from './m-16-status-update.component';

describe('Component Tests', () => {
  describe('M16Status Management Update Component', () => {
    let comp: M16StatusUpdateComponent;
    let fixture: ComponentFixture<M16StatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let m16StatusService: M16StatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [M16StatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(M16StatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(M16StatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      m16StatusService = TestBed.inject(M16StatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const m16Status: IM16Status = { id: 456 };

        activatedRoute.data = of({ m16Status });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(m16Status));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<M16Status>>();
        const m16Status = { id: 123 };
        jest.spyOn(m16StatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ m16Status });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: m16Status }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(m16StatusService.update).toHaveBeenCalledWith(m16Status);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<M16Status>>();
        const m16Status = new M16Status();
        jest.spyOn(m16StatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ m16Status });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: m16Status }));
        saveSubject.complete();

        // THEN
        expect(m16StatusService.create).toHaveBeenCalledWith(m16Status);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<M16Status>>();
        const m16Status = { id: 123 };
        jest.spyOn(m16StatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ m16Status });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(m16StatusService.update).toHaveBeenCalledWith(m16Status);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
