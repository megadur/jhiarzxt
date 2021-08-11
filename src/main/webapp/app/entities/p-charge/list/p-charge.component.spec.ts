import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PChargeService } from '../service/p-charge.service';

import { PChargeComponent } from './p-charge.component';

describe('Component Tests', () => {
  describe('PCharge Management Component', () => {
    let comp: PChargeComponent;
    let fixture: ComponentFixture<PChargeComponent>;
    let service: PChargeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PChargeComponent],
      })
        .overrideTemplate(PChargeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PChargeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PChargeService);

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
      expect(comp.pCharges?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
