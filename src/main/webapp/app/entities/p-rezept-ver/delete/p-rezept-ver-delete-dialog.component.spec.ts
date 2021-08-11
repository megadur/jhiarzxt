jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PRezeptVerService } from '../service/p-rezept-ver.service';

import { PRezeptVerDeleteDialogComponent } from './p-rezept-ver-delete-dialog.component';

describe('Component Tests', () => {
  describe('PRezeptVer Management Delete Component', () => {
    let comp: PRezeptVerDeleteDialogComponent;
    let fixture: ComponentFixture<PRezeptVerDeleteDialogComponent>;
    let service: PRezeptVerService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptVerDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(PRezeptVerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PRezeptVerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PRezeptVerService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
