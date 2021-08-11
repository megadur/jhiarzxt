import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { Muster16AbgService } from '../service/muster-16-abg.service';

import { Muster16AbgComponent } from './muster-16-abg.component';

describe('Component Tests', () => {
  describe('Muster16Abg Management Component', () => {
    let comp: Muster16AbgComponent;
    let fixture: ComponentFixture<Muster16AbgComponent>;
    let service: Muster16AbgService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16AbgComponent],
      })
        .overrideTemplate(Muster16AbgComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16AbgComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Muster16AbgService);

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
      expect(comp.muster16Abgs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
