jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RechenzentrumService } from '../service/rechenzentrum.service';
import { IRechenzentrum, Rechenzentrum } from '../rechenzentrum.model';

import { RechenzentrumUpdateComponent } from './rechenzentrum-update.component';

describe('Component Tests', () => {
  describe('Rechenzentrum Management Update Component', () => {
    let comp: RechenzentrumUpdateComponent;
    let fixture: ComponentFixture<RechenzentrumUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let rechenzentrumService: RechenzentrumService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RechenzentrumUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RechenzentrumUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RechenzentrumUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      rechenzentrumService = TestBed.inject(RechenzentrumService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const rechenzentrum: IRechenzentrum = { id: 456 };

        activatedRoute.data = of({ rechenzentrum });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(rechenzentrum));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rechenzentrum>>();
        const rechenzentrum = { id: 123 };
        jest.spyOn(rechenzentrumService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rechenzentrum });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rechenzentrum }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(rechenzentrumService.update).toHaveBeenCalledWith(rechenzentrum);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rechenzentrum>>();
        const rechenzentrum = new Rechenzentrum();
        jest.spyOn(rechenzentrumService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rechenzentrum });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rechenzentrum }));
        saveSubject.complete();

        // THEN
        expect(rechenzentrumService.create).toHaveBeenCalledWith(rechenzentrum);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rechenzentrum>>();
        const rechenzentrum = { id: 123 };
        jest.spyOn(rechenzentrumService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rechenzentrum });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(rechenzentrumService.update).toHaveBeenCalledWith(rechenzentrum);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
