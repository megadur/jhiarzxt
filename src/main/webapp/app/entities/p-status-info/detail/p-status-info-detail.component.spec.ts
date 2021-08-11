import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PStatusInfoDetailComponent } from './p-status-info-detail.component';

describe('Component Tests', () => {
  describe('PStatusInfo Management Detail Component', () => {
    let comp: PStatusInfoDetailComponent;
    let fixture: ComponentFixture<PStatusInfoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PStatusInfoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pStatusInfo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PStatusInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PStatusInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pStatusInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pStatusInfo).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
