jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Muster16AbrService } from '../service/muster-16-abr.service';

import { Muster16AbrDeleteDialogComponent } from './muster-16-abr-delete-dialog.component';

describe('Component Tests', () => {
  describe('Muster16Abr Management Delete Component', () => {
    let comp: Muster16AbrDeleteDialogComponent;
    let fixture: ComponentFixture<Muster16AbrDeleteDialogComponent>;
    let service: Muster16AbrService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16AbrDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(Muster16AbrDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Muster16AbrDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Muster16AbrService);
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
