import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { M16StatusService } from '../service/m-16-status.service';

import { M16StatusComponent } from './m-16-status.component';

describe('Component Tests', () => {
  describe('M16Status Management Component', () => {
    let comp: M16StatusComponent;
    let fixture: ComponentFixture<M16StatusComponent>;
    let service: M16StatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [M16StatusComponent],
      })
        .overrideTemplate(M16StatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(M16StatusComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(M16StatusService);

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
      expect(comp.m16Statuses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
