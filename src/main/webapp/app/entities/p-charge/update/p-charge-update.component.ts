import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPCharge, PCharge } from '../p-charge.model';
import { PChargeService } from '../service/p-charge.service';
import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';
import { PRezeptService } from 'app/entities/p-rezept/service/p-rezept.service';

@Component({
  selector: 'jhi-p-charge-update',
  templateUrl: './p-charge-update.component.html',
})
export class PChargeUpdateComponent implements OnInit {
  isSaving = false;

  pRezeptsSharedCollection: IPRezept[] = [];

  editForm = this.fb.group({
    id: [],
    pChargeId: [],
    pRezeptId: [],
    anzahlApplikationen: [],
    apoIk: [],
    chargenNr: [],
    herstellerNr: [],
    herstellerSchluessel: [],
    herstellungsDatum: [],
    pRezept: [],
  });

  constructor(
    protected pChargeService: PChargeService,
    protected pRezeptService: PRezeptService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pCharge }) => {
      this.updateForm(pCharge);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pCharge = this.createFromForm();
    if (pCharge.id !== undefined) {
      this.subscribeToSaveResponse(this.pChargeService.update(pCharge));
    } else {
      this.subscribeToSaveResponse(this.pChargeService.create(pCharge));
    }
  }

  trackPRezeptById(index: number, item: IPRezept): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPCharge>>): void {
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

  protected updateForm(pCharge: IPCharge): void {
    this.editForm.patchValue({
      id: pCharge.id,
      pChargeId: pCharge.pChargeId,
      pRezeptId: pCharge.pRezeptId,
      anzahlApplikationen: pCharge.anzahlApplikationen,
      apoIk: pCharge.apoIk,
      chargenNr: pCharge.chargenNr,
      herstellerNr: pCharge.herstellerNr,
      herstellerSchluessel: pCharge.herstellerSchluessel,
      herstellungsDatum: pCharge.herstellungsDatum,
      pRezept: pCharge.pRezept,
    });

    this.pRezeptsSharedCollection = this.pRezeptService.addPRezeptToCollectionIfMissing(this.pRezeptsSharedCollection, pCharge.pRezept);
  }

  protected loadRelationshipsOptions(): void {
    this.pRezeptService
      .query()
      .pipe(map((res: HttpResponse<IPRezept[]>) => res.body ?? []))
      .pipe(
        map((pRezepts: IPRezept[]) => this.pRezeptService.addPRezeptToCollectionIfMissing(pRezepts, this.editForm.get('pRezept')!.value))
      )
      .subscribe((pRezepts: IPRezept[]) => (this.pRezeptsSharedCollection = pRezepts));
  }

  protected createFromForm(): IPCharge {
    return {
      ...new PCharge(),
      id: this.editForm.get(['id'])!.value,
      pChargeId: this.editForm.get(['pChargeId'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
      anzahlApplikationen: this.editForm.get(['anzahlApplikationen'])!.value,
      apoIk: this.editForm.get(['apoIk'])!.value,
      chargenNr: this.editForm.get(['chargenNr'])!.value,
      herstellerNr: this.editForm.get(['herstellerNr'])!.value,
      herstellerSchluessel: this.editForm.get(['herstellerSchluessel'])!.value,
      herstellungsDatum: this.editForm.get(['herstellungsDatum'])!.value,
      pRezept: this.editForm.get(['pRezept'])!.value,
    };
  }
}
