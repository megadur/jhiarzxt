jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Muster16VerService } from '../service/muster-16-ver.service';

import { Muster16VerDeleteDialogComponent } from './muster-16-ver-delete-dialog.component';

describe('Component Tests', () => {
  describe('Muster16Ver Management Delete Component', () => {
    let comp: Muster16VerDeleteDialogComponent;
    let fixture: ComponentFixture<Muster16VerDeleteDialogComponent>;
    let service: Muster16VerService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16VerDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(Muster16VerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Muster16VerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Muster16VerService);
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
