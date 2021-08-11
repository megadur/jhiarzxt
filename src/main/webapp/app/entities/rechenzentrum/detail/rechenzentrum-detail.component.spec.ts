import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RechenzentrumDetailComponent } from './rechenzentrum-detail.component';

describe('Component Tests', () => {
  describe('Rechenzentrum Management Detail Component', () => {
    let comp: RechenzentrumDetailComponent;
    let fixture: ComponentFixture<RechenzentrumDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RechenzentrumDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ rechenzentrum: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RechenzentrumDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RechenzentrumDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rechenzentrum on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rechenzentrum).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
