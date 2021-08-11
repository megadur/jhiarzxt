jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EAenderungService } from '../service/e-aenderung.service';

import { EAenderungDeleteDialogComponent } from './e-aenderung-delete-dialog.component';

describe('Component Tests', () => {
  describe('EAenderung Management Delete Component', () => {
    let comp: EAenderungDeleteDialogComponent;
    let fixture: ComponentFixture<EAenderungDeleteDialogComponent>;
    let service: EAenderungService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EAenderungDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(EAenderungDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EAenderungDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EAenderungService);
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
