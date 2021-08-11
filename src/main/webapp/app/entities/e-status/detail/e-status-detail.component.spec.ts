import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EStatusDetailComponent } from './e-status-detail.component';

describe('Component Tests', () => {
  describe('EStatus Management Detail Component', () => {
    let comp: EStatusDetailComponent;
    let fixture: ComponentFixture<EStatusDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EStatusDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ eStatus: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eStatus).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
