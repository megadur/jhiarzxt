import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEAenderung, EAenderung } from '../e-aenderung.model';
import { EAenderungService } from '../service/e-aenderung.service';
import { IERezept } from 'app/entities/e-rezept/e-rezept.model';
import { ERezeptService } from 'app/entities/e-rezept/service/e-rezept.service';

@Component({
  selector: 'jhi-e-aenderung-update',
  templateUrl: './e-aenderung-update.component.html',
})
export class EAenderungUpdateComponent implements OnInit {
  isSaving = false;

  eRezeptsSharedCollection: IERezept[] = [];

  editForm = this.fb.group({
    id: [],
    schluessel: [],
    dokuRezept: [],
    dokuArzt: [],
    datum: [],
    eRezept: [],
  });

  constructor(
    protected eAenderungService: EAenderungService,
    protected eRezeptService: ERezeptService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eAenderung }) => {
      this.updateForm(eAenderung);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eAenderung = this.createFromForm();
    if (eAenderung.id !== undefined) {
      this.subscribeToSaveResponse(this.eAenderungService.update(eAenderung));
    } else {
      this.subscribeToSaveResponse(this.eAenderungService.create(eAenderung));
    }
  }

  trackERezeptById(index: number, item: IERezept): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEAenderung>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(eAenderung: IEAenderung): void {
    this.editForm.patchValue({
      id: eAenderung.id,
      schluessel: eAenderung.schluessel,
      dokuRezept: eAenderung.dokuRezept,
      dokuArzt: eAenderung.dokuArzt,
      datum: eAenderung.datum,
      eRezept: eAenderung.eRezept,
    });

    this.eRezeptsSharedCollection = this.eRezeptService.addERezeptToCollectionIfMissing(this.eRezeptsSharedCollection, eAenderung.eRezept);
  }

  protected loadRelationshipsOptions(): void {
    this.eRezeptService
      .query()
      .pipe(map((res: HttpResponse<IERezept[]>) => res.body ?? []))
      .pipe(
        map((eRezepts: IERezept[]) => this.eRezeptService.addERezeptToCollectionIfMissing(eRezepts, this.editForm.get('eRezept')!.value))
      )
      .subscribe((eRezepts: IERezept[]) => (this.eRezeptsSharedCollection = eRezepts));
  }

  protected createFromForm(): IEAenderung {
    return {
      ...new EAenderung(),
      id: this.editForm.get(['id'])!.value,
      schluessel: this.editForm.get(['schluessel'])!.value,
      dokuRezept: this.editForm.get(['dokuRezept'])!.value,
      dokuArzt: this.editForm.get(['dokuArzt'])!.value,
      datum: this.editForm.get(['datum'])!.value,
      eRezept: this.editForm.get(['eRezept'])!.value,
    };
  }
}
