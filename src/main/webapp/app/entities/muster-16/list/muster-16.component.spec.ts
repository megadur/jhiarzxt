import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { Muster16Service } from '../service/muster-16.service';

import { Muster16Component } from './muster-16.component';

describe('Component Tests', () => {
  describe('Muster16 Management Component', () => {
    let comp: Muster16Component;
    let fixture: ComponentFixture<Muster16Component>;
    let service: Muster16Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16Component],
      })
        .overrideTemplate(Muster16Component, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16Component);
      comp = fixture.componentInstance;
      service = TestBed.inject(Muster16Service);

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
      expect(comp.muster16s?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
