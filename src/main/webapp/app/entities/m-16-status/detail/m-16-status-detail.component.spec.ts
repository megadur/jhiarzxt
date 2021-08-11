import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { M16StatusDetailComponent } from './m-16-status-detail.component';

describe('Component Tests', () => {
  describe('M16Status Management Detail Component', () => {
    let comp: M16StatusDetailComponent;
    let fixture: ComponentFixture<M16StatusDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [M16StatusDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ m16Status: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(M16StatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(M16StatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load m16Status on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.m16Status).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
