import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RechenzentrumService } from '../service/rechenzentrum.service';

import { RechenzentrumComponent } from './rechenzentrum.component';

describe('Component Tests', () => {
  describe('Rechenzentrum Management Component', () => {
    let comp: RechenzentrumComponent;
    let fixture: ComponentFixture<RechenzentrumComponent>;
    let service: RechenzentrumService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RechenzentrumComponent],
      })
        .overrideTemplate(RechenzentrumComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RechenzentrumComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(RechenzentrumService);

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
      expect(comp.rechenzentrums?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
