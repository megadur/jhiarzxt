jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { M16StatusService } from '../service/m-16-status.service';

import { M16StatusDeleteDialogComponent } from './m-16-status-delete-dialog.component';

describe('Component Tests', () => {
  describe('M16Status Management Delete Component', () => {
    let comp: M16StatusDeleteDialogComponent;
    let fixture: ComponentFixture<M16StatusDeleteDialogComponent>;
    let service: M16StatusService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [M16StatusDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(M16StatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(M16StatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(M16StatusService);
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
