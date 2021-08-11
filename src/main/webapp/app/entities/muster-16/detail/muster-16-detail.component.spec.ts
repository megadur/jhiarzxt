import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Muster16DetailComponent } from './muster-16-detail.component';

describe('Component Tests', () => {
  describe('Muster16 Management Detail Component', () => {
    let comp: Muster16DetailComponent;
    let fixture: ComponentFixture<Muster16DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Muster16DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ muster16: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Muster16DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Muster16DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load muster16 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.muster16).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
