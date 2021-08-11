import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PChargeDetailComponent } from './p-charge-detail.component';

describe('Component Tests', () => {
  describe('PCharge Management Detail Component', () => {
    let comp: PChargeDetailComponent;
    let fixture: ComponentFixture<PChargeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PChargeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pCharge: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PChargeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PChargeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pCharge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pCharge).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
