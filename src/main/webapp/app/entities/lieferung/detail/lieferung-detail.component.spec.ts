import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LieferungDetailComponent } from './lieferung-detail.component';

describe('Component Tests', () => {
  describe('Lieferung Management Detail Component', () => {
    let comp: LieferungDetailComponent;
    let fixture: ComponentFixture<LieferungDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [LieferungDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ lieferung: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(LieferungDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LieferungDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lieferung on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lieferung).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
