import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PStatusInfoService } from '../service/p-status-info.service';

import { PStatusInfoComponent } from './p-status-info.component';

describe('Component Tests', () => {
  describe('PStatusInfo Management Component', () => {
    let comp: PStatusInfoComponent;
    let fixture: ComponentFixture<PStatusInfoComponent>;
    let service: PStatusInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PStatusInfoComponent],
      })
        .overrideTemplate(PStatusInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PStatusInfoComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PStatusInfoService);

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
      expect(comp.pStatusInfos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
