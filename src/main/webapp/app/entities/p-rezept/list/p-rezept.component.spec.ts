import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PRezeptService } from '../service/p-rezept.service';

import { PRezeptComponent } from './p-rezept.component';

describe('Component Tests', () => {
  describe('PRezept Management Component', () => {
    let comp: PRezeptComponent;
    let fixture: ComponentFixture<PRezeptComponent>;
    let service: PRezeptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PRezeptComponent],
      })
        .overrideTemplate(PRezeptComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PRezeptComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PRezeptService);

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
      expect(comp.pRezepts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
