import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LieferungService } from '../service/lieferung.service';

import { LieferungComponent } from './lieferung.component';

describe('Component Tests', () => {
  describe('Lieferung Management Component', () => {
    let comp: LieferungComponent;
    let fixture: ComponentFixture<LieferungComponent>;
    let service: LieferungService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LieferungComponent],
      })
        .overrideTemplate(LieferungComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LieferungComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(LieferungService);

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
      expect(comp.lieferungs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
