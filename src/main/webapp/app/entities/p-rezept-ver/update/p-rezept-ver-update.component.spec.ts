jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PRezeptVerService } from '../service/p-rezept-ver.service';
import { IPRezeptVer, PRezeptVer } from '../p-rezept-ver.model';
import { IPRezeptAbg } from 'app/entities/p-rezept-abg/p-rezept-abg.model';
import { PRezeptAbgService } from 'app/entities/p-rezept-abg/service/p-rezept-abg.service';

import { PRezeptVerUpdateComponent } from './p-rezept-ver-update.component';

describe('Component Tests', () => {
  describe('PRezeptVer Management Update Component', () => {
    let comp: PRezeptVerUpdateComponent;
    let fixture: ComponentFixture<PRezeptVerUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pRezeptVerService: PRezeptVerService;
    let pRezeptAbgService: PRezeptAbgService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptVerUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PRezeptVerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PRezeptVerUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pRezeptVerService = TestBed.inject(PRezeptVerService);
      pRezeptAbgService = TestBed.inject(PRezeptAbgService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call pRezeptId query and add missing value', () => {
        const pRezeptVer: IPRezeptVer = { id: 456 };
        const pRezeptId: IPRezeptAbg = { id: 30438 };
        pRezeptVer.pRezeptId = pRezeptId;

        const pRezeptIdCollection: IPRezeptAbg[] = [{ id: 56278 }];
        jest.spyOn(pRezeptAbgService, 'query').mockReturnValue(of(new HttpResponse({ body: pRezeptIdCollection })));
        const expectedCollection: IPRezeptAbg[] = [pRezeptId, ...pRezeptIdCollection];
        jest.spyOn(pRezeptAbgService, 'addPRezeptAbgToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ pRezeptVer });
        comp.ngOnInit();

        expect(pRezeptAbgService.query).toHaveBeenCalled();
        expect(pRezeptAbgService.addPRezeptAbgToCollectionIfMissing).toHaveBeenCalledWith(pRezeptIdCollection, pRezeptId);
        expect(comp.pRezeptIdsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const pRezeptVer: IPRezeptVer = { id: 456 };
        const pRezeptId: IPRezeptAbg = { id: 28766 };
        pRezeptVer.pRezeptId = pRezeptId;

        activatedRoute.data = of({ pRezeptVer });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pRezeptVer));
        expect(comp.pRezeptIdsCollection).toContain(pRezeptId);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptVer>>();
        const pRezeptVer = { id: 123 };
        jest.spyOn(pRezeptVerService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptVer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezeptVer }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pRezeptVerService.update).toHaveBeenCalledWith(pRezeptVer);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptVer>>();
        const pRezeptVer = new PRezeptVer();
        jest.spyOn(pRezeptVerService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptVer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pRezeptVer }));
        saveSubject.complete();

        // THEN
        expect(pRezeptVerService.create).toHaveBeenCalledWith(pRezeptVer);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PRezeptVer>>();
        const pRezeptVer = { id: 123 };
        jest.spyOn(pRezeptVerService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pRezeptVer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pRezeptVerService.update).toHaveBeenCalledWith(pRezeptVer);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPRezeptAbgById', () => {
        it('Should return tracked PRezeptAbg primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPRezeptAbgById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
