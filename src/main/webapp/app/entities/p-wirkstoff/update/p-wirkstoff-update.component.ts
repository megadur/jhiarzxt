import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPWirkstoff, PWirkstoff } from '../p-wirkstoff.model';
import { PWirkstoffService } from '../service/p-wirkstoff.service';
import { IPCharge } from 'app/entities/p-charge/p-charge.model';
import { PChargeService } from 'app/entities/p-charge/service/p-charge.service';

@Component({
  selector: 'jhi-p-wirkstoff-update',
  templateUrl: './p-wirkstoff-update.component.html',
})
export class PWirkstoffUpdateComponent implements OnInit {
  isSaving = false;

  pChargesSharedCollection: IPCharge[] = [];

  editForm = this.fb.group({
    id: [],
    pWirkstoffId: [],
    pChargeId: [],
    apoIk: [],
    faktor: [],
    faktorKennzeichen: [],
    notiz: [],
    pPosNr: [],
    preisKennzeichen: [],
    pzn: [],
    taxe: [],
    wirkstoffName: [],
    pCharge: [],
  });

  constructor(
    protected pWirkstoffService: PWirkstoffService,
    protected pChargeService: PChargeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pWirkstoff }) => {
      this.updateForm(pWirkstoff);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pWirkstoff = this.createFromForm();
    if (pWirkstoff.id !== undefined) {
      this.subscribeToSaveResponse(this.pWirkstoffService.update(pWirkstoff));
    } else {
      this.subscribeToSaveResponse(this.pWirkstoffService.create(pWirkstoff));
    }
  }

  trackPChargeById(index: number, item: IPCharge): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPWirkstoff>>): void {
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

  protected updateForm(pWirkstoff: IPWirkstoff): void {
    this.editForm.patchValue({
      id: pWirkstoff.id,
      pWirkstoffId: pWirkstoff.pWirkstoffId,
      pChargeId: pWirkstoff.pChargeId,
      apoIk: pWirkstoff.apoIk,
      faktor: pWirkstoff.faktor,
      faktorKennzeichen: pWirkstoff.faktorKennzeichen,
      notiz: pWirkstoff.notiz,
      pPosNr: pWirkstoff.pPosNr,
      preisKennzeichen: pWirkstoff.preisKennzeichen,
      pzn: pWirkstoff.pzn,
      taxe: pWirkstoff.taxe,
      wirkstoffName: pWirkstoff.wirkstoffName,
      pCharge: pWirkstoff.pCharge,
    });

    this.pChargesSharedCollection = this.pChargeService.addPChargeToCollectionIfMissing(this.pChargesSharedCollection, pWirkstoff.pCharge);
  }

  protected loadRelationshipsOptions(): void {
    this.pChargeService
      .query()
      .pipe(map((res: HttpResponse<IPCharge[]>) => res.body ?? []))
      .pipe(
        map((pCharges: IPCharge[]) => this.pChargeService.addPChargeToCollectionIfMissing(pCharges, this.editForm.get('pCharge')!.value))
      )
      .subscribe((pCharges: IPCharge[]) => (this.pChargesSharedCollection = pCharges));
  }

  protected createFromForm(): IPWirkstoff {
    return {
      ...new PWirkstoff(),
      id: this.editForm.get(['id'])!.value,
      pWirkstoffId: this.editForm.get(['pWirkstoffId'])!.value,
      pChargeId: this.editForm.get(['pChargeId'])!.value,
      apoIk: this.editForm.get(['apoIk'])!.value,
      faktor: this.editForm.get(['faktor'])!.value,
      faktorKennzeichen: this.editForm.get(['faktorKennzeichen'])!.value,
      notiz: this.editForm.get(['notiz'])!.value,
      pPosNr: this.editForm.get(['pPosNr'])!.value,
      preisKennzeichen: this.editForm.get(['preisKennzeichen'])!.value,
      pzn: this.editForm.get(['pzn'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      wirkstoffName: this.editForm.get(['wirkstoffName'])!.value,
      pCharge: this.editForm.get(['pCharge'])!.value,
    };
  }
}
