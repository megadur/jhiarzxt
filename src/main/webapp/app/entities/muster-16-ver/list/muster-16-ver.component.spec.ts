import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { Muster16VerService } from '../service/muster-16-ver.service';

import { Muster16VerComponent } from './muster-16-ver.component';

describe('Component Tests', () => {
  describe('Muster16Ver Management Component', () => {
    let comp: Muster16VerComponent;
    let fixture: ComponentFixture<Muster16VerComponent>;
    let service: Muster16VerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16VerComponent],
      })
        .overrideTemplate(Muster16VerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16VerComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Muster16VerService);

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
      expect(comp.muster16Vers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
