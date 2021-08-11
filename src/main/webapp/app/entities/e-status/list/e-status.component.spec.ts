import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EStatusService } from '../service/e-status.service';

import { EStatusComponent } from './e-status.component';

describe('Component Tests', () => {
  describe('EStatus Management Component', () => {
    let comp: EStatusComponent;
    let fixture: ComponentFixture<EStatusComponent>;
    let service: EStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EStatusComponent],
      })
        .overrideTemplate(EStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EStatusComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EStatusService);

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
      expect(comp.eStatuses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
