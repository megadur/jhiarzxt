import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EAenderungService } from '../service/e-aenderung.service';

import { EAenderungComponent } from './e-aenderung.component';

describe('Component Tests', () => {
  describe('EAenderung Management Component', () => {
    let comp: EAenderungComponent;
    let fixture: ComponentFixture<EAenderungComponent>;
    let service: EAenderungService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EAenderungComponent],
      })
        .overrideTemplate(EAenderungComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EAenderungComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EAenderungService);

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
      expect(comp.eAenderungs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
