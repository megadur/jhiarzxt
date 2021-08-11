import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ERezeptService } from '../service/e-rezept.service';

import { ERezeptComponent } from './e-rezept.component';

describe('Component Tests', () => {
  describe('ERezept Management Component', () => {
    let comp: ERezeptComponent;
    let fixture: ComponentFixture<ERezeptComponent>;
    let service: ERezeptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ERezeptComponent],
      })
        .overrideTemplate(ERezeptComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ERezeptComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ERezeptService);

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
      expect(comp.eRezepts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
