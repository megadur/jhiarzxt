import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ApothekeService } from '../service/apotheke.service';

import { ApothekeComponent } from './apotheke.component';

describe('Component Tests', () => {
  describe('Apotheke Management Component', () => {
    let comp: ApothekeComponent;
    let fixture: ComponentFixture<ApothekeComponent>;
    let service: ApothekeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ApothekeComponent],
      })
        .overrideTemplate(ApothekeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApothekeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ApothekeService);

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
      expect(comp.apothekes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
