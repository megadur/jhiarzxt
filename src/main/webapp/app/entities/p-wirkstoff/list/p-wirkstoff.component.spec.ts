import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PWirkstoffService } from '../service/p-wirkstoff.service';

import { PWirkstoffComponent } from './p-wirkstoff.component';

describe('Component Tests', () => {
  describe('PWirkstoff Management Component', () => {
    let comp: PWirkstoffComponent;
    let fixture: ComponentFixture<PWirkstoffComponent>;
    let service: PWirkstoffService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PWirkstoffComponent],
      })
        .overrideTemplate(PWirkstoffComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PWirkstoffComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PWirkstoffService);

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
      expect(comp.pWirkstoffs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
