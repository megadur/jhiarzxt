jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Muster16AbgService } from '../service/muster-16-abg.service';

import { Muster16AbgDeleteDialogComponent } from './muster-16-abg-delete-dialog.component';

describe('Component Tests', () => {
  describe('Muster16Abg Management Delete Component', () => {
    let comp: Muster16AbgDeleteDialogComponent;
    let fixture: ComponentFixture<Muster16AbgDeleteDialogComponent>;
    let service: Muster16AbgService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16AbgDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(Muster16AbgDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Muster16AbgDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Muster16AbgService);
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
