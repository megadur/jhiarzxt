jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LieferungService } from '../service/lieferung.service';
import { ILieferung, Lieferung } from '../lieferung.model';
import { IApotheke } from 'app/entities/apotheke/apotheke.model';
import { ApothekeService } from 'app/entities/apotheke/service/apotheke.service';

import { LieferungUpdateComponent } from './lieferung-update.component';

describe('Component Tests', () => {
  describe('Lieferung Management Update Component', () => {
    let comp: LieferungUpdateComponent;
    let fixture: ComponentFixture<LieferungUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let lieferungService: LieferungService;
    let apothekeService: ApothekeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LieferungUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LieferungUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LieferungUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      lieferungService = TestBed.inject(LieferungService);
      apothekeService = TestBed.inject(ApothekeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Apotheke query and add missing value', () => {
        const lieferung: ILieferung = { id: 456 };
        const apotheke: IApotheke = { id: 45115 };
        lieferung.apotheke = apotheke;

        const apothekeCollection: IApotheke[] = [{ id: 88143 }];
        jest.spyOn(apothekeService, 'query').mockReturnValue(of(new HttpResponse({ body: apothekeCollection })));
        const additionalApothekes = [apotheke];
        const expectedCollection: IApotheke[] = [...additionalApothekes, ...apothekeCollection];
        jest.spyOn(apothekeService, 'addApothekeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ lieferung });
        comp.ngOnInit();

        expect(apothekeService.query).toHaveBeenCalled();
        expect(apothekeService.addApothekeToCollectionIfMissing).toHaveBeenCalledWith(apothekeCollection, ...additionalApothekes);
        expect(comp.apothekesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const lieferung: ILieferung = { id: 456 };
        const apotheke: IApotheke = { id: 38414 };
        lieferung.apotheke = apotheke;

        activatedRoute.data = of({ lieferung });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(lieferung));
        expect(comp.apothekesSharedCollection).toContain(apotheke);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Lieferung>>();
        const lieferung = { id: 123 };
        jest.spyOn(lieferungService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ lieferung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: lieferung }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(lieferungService.update).toHaveBeenCalledWith(lieferung);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Lieferung>>();
        const lieferung = new Lieferung();
        jest.spyOn(lieferungService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ lieferung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: lieferung }));
        saveSubject.complete();

        // THEN
        expect(lieferungService.create).toHaveBeenCalledWith(lieferung);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Lieferung>>();
        const lieferung = { id: 123 };
        jest.spyOn(lieferungService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ lieferung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(lieferungService.update).toHaveBeenCalledWith(lieferung);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackApothekeById', () => {
        it('Should return tracked Apotheke primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackApothekeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
