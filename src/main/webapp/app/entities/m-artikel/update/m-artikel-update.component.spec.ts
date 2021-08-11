jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MArtikelService } from '../service/m-artikel.service';
import { IMArtikel, MArtikel } from '../m-artikel.model';
import { IMuster16 } from 'app/entities/muster-16/muster-16.model';
import { Muster16Service } from 'app/entities/muster-16/service/muster-16.service';

import { MArtikelUpdateComponent } from './m-artikel-update.component';

describe('Component Tests', () => {
  describe('MArtikel Management Update Component', () => {
    let comp: MArtikelUpdateComponent;
    let fixture: ComponentFixture<MArtikelUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let mArtikelService: MArtikelService;
    let muster16Service: Muster16Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MArtikelUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MArtikelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MArtikelUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      mArtikelService = TestBed.inject(MArtikelService);
      muster16Service = TestBed.inject(Muster16Service);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Muster16 query and add missing value', () => {
        const mArtikel: IMArtikel = { id: 456 };
        const muster16: IMuster16 = { id: 52806 };
        mArtikel.muster16 = muster16;

        const muster16Collection: IMuster16[] = [{ id: 62209 }];
        jest.spyOn(muster16Service, 'query').mockReturnValue(of(new HttpResponse({ body: muster16Collection })));
        const additionalMuster16s = [muster16];
        const expectedCollection: IMuster16[] = [...additionalMuster16s, ...muster16Collection];
        jest.spyOn(muster16Service, 'addMuster16ToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ mArtikel });
        comp.ngOnInit();

        expect(muster16Service.query).toHaveBeenCalled();
        expect(muster16Service.addMuster16ToCollectionIfMissing).toHaveBeenCalledWith(muster16Collection, ...additionalMuster16s);
        expect(comp.muster16sSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const mArtikel: IMArtikel = { id: 456 };
        const muster16: IMuster16 = { id: 34093 };
        mArtikel.muster16 = muster16;

        activatedRoute.data = of({ mArtikel });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(mArtikel));
        expect(comp.muster16sSharedCollection).toContain(muster16);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<MArtikel>>();
        const mArtikel = { id: 123 };
        jest.spyOn(mArtikelService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ mArtikel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mArtikel }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(mArtikelService.update).toHaveBeenCalledWith(mArtikel);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<MArtikel>>();
        const mArtikel = new MArtikel();
        jest.spyOn(mArtikelService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ mArtikel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mArtikel }));
        saveSubject.complete();

        // THEN
        expect(mArtikelService.create).toHaveBeenCalledWith(mArtikel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<MArtikel>>();
        const mArtikel = { id: 123 };
        jest.spyOn(mArtikelService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ mArtikel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(mArtikelService.update).toHaveBeenCalledWith(mArtikel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMuster16ById', () => {
        it('Should return tracked Muster16 primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMuster16ById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
