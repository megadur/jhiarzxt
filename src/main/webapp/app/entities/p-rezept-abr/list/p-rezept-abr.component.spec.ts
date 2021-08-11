import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PRezeptAbrService } from '../service/p-rezept-abr.service';

import { PRezeptAbrComponent } from './p-rezept-abr.component';

describe('Component Tests', () => {
  describe('PRezeptAbr Management Component', () => {
    let comp: PRezeptAbrComponent;
    let fixture: ComponentFixture<PRezeptAbrComponent>;
    let service: PRezeptAbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptAbrComponent],
      })
        .overrideTemplate(PRezeptAbrComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PRezeptAbrComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PRezeptAbrService);

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
      expect(comp.pRezeptAbrs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
