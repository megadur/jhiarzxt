import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PWirkstoffDetailComponent } from './p-wirkstoff-detail.component';

describe('Component Tests', () => {
  describe('PWirkstoff Management Detail Component', () => {
    let comp: PWirkstoffDetailComponent;
    let fixture: ComponentFixture<PWirkstoffDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PWirkstoffDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pWirkstoff: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PWirkstoffDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PWirkstoffDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pWirkstoff on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pWirkstoff).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
