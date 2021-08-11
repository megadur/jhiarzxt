import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PStatusDetailComponent } from './p-status-detail.component';

describe('Component Tests', () => {
  describe('PStatus Management Detail Component', () => {
    let comp: PStatusDetailComponent;
    let fixture: ComponentFixture<PStatusDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PStatusDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pStatus: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pStatus).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
