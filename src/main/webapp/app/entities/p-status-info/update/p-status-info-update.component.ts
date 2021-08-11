import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPStatusInfo, PStatusInfo } from '../p-status-info.model';
import { PStatusInfoService } from '../service/p-status-info.service';
import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';
import { PRezeptService } from 'app/entities/p-rezept/service/p-rezept.service';

@Component({
  selector: 'jhi-p-status-info-update',
  templateUrl: './p-status-info-update.component.html',
})
export class PStatusInfoUpdateComponent implements OnInit {
  isSaving = false;

  pRezeptsSharedCollection: IPRezept[] = [];

  editForm = this.fb.group({
    id: [],
    pStatusInfoId: [],
    pRezeptId: [],
    apoIk: [],
    fCode: [],
    fHauptFehler: [],
    fKommentar: [],
    fKurzText: [],
    fLangText: [],
    fStatus: [],
    fTCode: [],
    fWert: [],
    faktor: [],
    fristEnde: [],
    gesBrutto: [],
    posNr: [],
    taxe: [],
    zeitpunkt: [],
    zuzahlung: [],
    pStatusInfoId: [],
  });

  constructor(
    protected pStatusInfoService: PStatusInfoService,
    protected pRezeptService: PRezeptService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pStatusInfo }) => {
      this.updateForm(pStatusInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pStatusInfo = this.createFromForm();
    if (pStatusInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.pStatusInfoService.update(pStatusInfo));
    } else {
      this.subscribeToSaveResponse(this.pStatusInfoService.create(pStatusInfo));
    }
  }

  trackPRezeptById(index: number, item: IPRezept): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPStatusInfo>>): void {
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

  protected updateForm(pStatusInfo: IPStatusInfo): void {
    this.editForm.patchValue({
      id: pStatusInfo.id,
      pStatusInfoId: pStatusInfo.pStatusInfoId,
      pRezeptId: pStatusInfo.pRezeptId,
      apoIk: pStatusInfo.apoIk,
      fCode: pStatusInfo.fCode,
      fHauptFehler: pStatusInfo.fHauptFehler,
      fKommentar: pStatusInfo.fKommentar,
      fKurzText: pStatusInfo.fKurzText,
      fLangText: pStatusInfo.fLangText,
      fStatus: pStatusInfo.fStatus,
      fTCode: pStatusInfo.fTCode,
      fWert: pStatusInfo.fWert,
      faktor: pStatusInfo.faktor,
      fristEnde: pStatusInfo.fristEnde,
      gesBrutto: pStatusInfo.gesBrutto,
      posNr: pStatusInfo.posNr,
      taxe: pStatusInfo.taxe,
      zeitpunkt: pStatusInfo.zeitpunkt,
      zuzahlung: pStatusInfo.zuzahlung,
      pStatusInfoId: pStatusInfo.pStatusInfoId,
    });

    this.pRezeptsSharedCollection = this.pRezeptService.addPRezeptToCollectionIfMissing(
      this.pRezeptsSharedCollection,
      pStatusInfo.pStatusInfoId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pRezeptService
      .query()
      .pipe(map((res: HttpResponse<IPRezept[]>) => res.body ?? []))
      .pipe(
        map((pRezepts: IPRezept[]) =>
          this.pRezeptService.addPRezeptToCollectionIfMissing(pRezepts, this.editForm.get('pStatusInfoId')!.value)
        )
      )
      .subscribe((pRezepts: IPRezept[]) => (this.pRezeptsSharedCollection = pRezepts));
  }

  protected createFromForm(): IPStatusInfo {
    return {
      ...new PStatusInfo(),
      id: this.editForm.get(['id'])!.value,
      pStatusInfoId: this.editForm.get(['pStatusInfoId'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
      apoIk: this.editForm.get(['apoIk'])!.value,
      fCode: this.editForm.get(['fCode'])!.value,
      fHauptFehler: this.editForm.get(['fHauptFehler'])!.value,
      fKommentar: this.editForm.get(['fKommentar'])!.value,
      fKurzText: this.editForm.get(['fKurzText'])!.value,
      fLangText: this.editForm.get(['fLangText'])!.value,
      fStatus: this.editForm.get(['fStatus'])!.value,
      fTCode: this.editForm.get(['fTCode'])!.value,
      fWert: this.editForm.get(['fWert'])!.value,
      faktor: this.editForm.get(['faktor'])!.value,
      fristEnde: this.editForm.get(['fristEnde'])!.value,
      gesBrutto: this.editForm.get(['gesBrutto'])!.value,
      posNr: this.editForm.get(['posNr'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      zeitpunkt: this.editForm.get(['zeitpunkt'])!.value,
      zuzahlung: this.editForm.get(['zuzahlung'])!.value,
      pStatusInfoId: this.editForm.get(['pStatusInfoId'])!.value,
    };
  }
}
