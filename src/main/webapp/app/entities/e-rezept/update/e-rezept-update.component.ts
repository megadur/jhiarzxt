import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IERezept, ERezept } from '../e-rezept.model';
import { ERezeptService } from '../service/e-rezept.service';
import { IEStatus } from 'app/entities/e-status/e-status.model';
import { EStatusService } from 'app/entities/e-status/service/e-status.service';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';
import { LieferungService } from 'app/entities/lieferung/service/lieferung.service';

@Component({
  selector: 'jhi-e-rezept-update',
  templateUrl: './e-rezept-update.component.html',
})
export class ERezeptUpdateComponent implements OnInit {
  isSaving = false;

  eStatusesCollection: IEStatus[] = [];
  lieferungsSharedCollection: ILieferung[] = [];

  editForm = this.fb.group({
    id: [],
    iD: [],
    dokVer: [],
    abgInfo: [],
    abgDatum: [],
    abgGesZuzahl: [],
    abgGesBrutto: [],
    abgVertragskz: [],
    eStatus: [],
    lieferungId: [],
  });

  constructor(
    protected eRezeptService: ERezeptService,
    protected eStatusService: EStatusService,
    protected lieferungService: LieferungService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eRezept }) => {
      this.updateForm(eRezept);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eRezept = this.createFromForm();
    if (eRezept.id !== undefined) {
      this.subscribeToSaveResponse(this.eRezeptService.update(eRezept));
    } else {
      this.subscribeToSaveResponse(this.eRezeptService.create(eRezept));
    }
  }

  trackEStatusById(index: number, item: IEStatus): number {
    return item.id!;
  }

  trackLieferungById(index: number, item: ILieferung): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IERezept>>): void {
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

  protected updateForm(eRezept: IERezept): void {
    this.editForm.patchValue({
      id: eRezept.id,
      iD: eRezept.iD,
      dokVer: eRezept.dokVer,
      abgInfo: eRezept.abgInfo,
      abgDatum: eRezept.abgDatum,
      abgGesZuzahl: eRezept.abgGesZuzahl,
      abgGesBrutto: eRezept.abgGesBrutto,
      abgVertragskz: eRezept.abgVertragskz,
      eStatus: eRezept.eStatus,
      lieferungId: eRezept.lieferungId,
    });

    this.eStatusesCollection = this.eStatusService.addEStatusToCollectionIfMissing(this.eStatusesCollection, eRezept.eStatus);
    this.lieferungsSharedCollection = this.lieferungService.addLieferungToCollectionIfMissing(
      this.lieferungsSharedCollection,
      eRezept.lieferungId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eStatusService
      .query({ filter: 'estatusid-is-null' })
      .pipe(map((res: HttpResponse<IEStatus[]>) => res.body ?? []))
      .pipe(
        map((eStatuses: IEStatus[]) => this.eStatusService.addEStatusToCollectionIfMissing(eStatuses, this.editForm.get('eStatus')!.value))
      )
      .subscribe((eStatuses: IEStatus[]) => (this.eStatusesCollection = eStatuses));

    this.lieferungService
      .query()
      .pipe(map((res: HttpResponse<ILieferung[]>) => res.body ?? []))
      .pipe(
        map((lieferungs: ILieferung[]) =>
          this.lieferungService.addLieferungToCollectionIfMissing(lieferungs, this.editForm.get('lieferungId')!.value)
        )
      )
      .subscribe((lieferungs: ILieferung[]) => (this.lieferungsSharedCollection = lieferungs));
  }

  protected createFromForm(): IERezept {
    return {
      ...new ERezept(),
      id: this.editForm.get(['id'])!.value,
      iD: this.editForm.get(['iD'])!.value,
      dokVer: this.editForm.get(['dokVer'])!.value,
      abgInfo: this.editForm.get(['abgInfo'])!.value,
      abgDatum: this.editForm.get(['abgDatum'])!.value,
      abgGesZuzahl: this.editForm.get(['abgGesZuzahl'])!.value,
      abgGesBrutto: this.editForm.get(['abgGesBrutto'])!.value,
      abgVertragskz: this.editForm.get(['abgVertragskz'])!.value,
      eStatus: this.editForm.get(['eStatus'])!.value,
      lieferungId: this.editForm.get(['lieferungId'])!.value,
    };
  }
}
