import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PStatusService } from '../service/p-status.service';

import { PStatusComponent } from './p-status.component';

describe('Component Tests', () => {
  describe('PStatus Management Component', () => {
    let comp: PStatusComponent;
    let fixture: ComponentFixture<PStatusComponent>;
    let service: PStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PStatusComponent],
      })
        .overrideTemplate(PStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PStatusComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PStatusService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pStatuses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
