import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { Muster16AbrService } from '../service/muster-16-abr.service';

import { Muster16AbrComponent } from './muster-16-abr.component';

describe('Component Tests', () => {
  describe('Muster16Abr Management Component', () => {
    let comp: Muster16AbrComponent;
    let fixture: ComponentFixture<Muster16AbrComponent>;
    let service: Muster16AbrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Muster16AbrComponent],
      })
        .overrideTemplate(Muster16AbrComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Muster16AbrComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Muster16AbrService);

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
      expect(comp.muster16Abrs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
