import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PRezeptVerService } from '../service/p-rezept-ver.service';

import { PRezeptVerComponent } from './p-rezept-ver.component';

describe('Component Tests', () => {
  describe('PRezeptVer Management Component', () => {
    let comp: PRezeptVerComponent;
    let fixture: ComponentFixture<PRezeptVerComponent>;
    let service: PRezeptVerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptVerComponent],
      })
        .overrideTemplate(PRezeptVerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PRezeptVerComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PRezeptVerService);

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
      expect(comp.pRezeptVers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
