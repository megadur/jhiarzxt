import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPRezept, PRezept } from '../p-rezept.model';
import { PRezeptService } from '../service/p-rezept.service';
import { IPStatus } from 'app/entities/p-status/p-status.model';
import { PStatusService } from 'app/entities/p-status/service/p-status.service';
import { IPRezeptVer } from 'app/entities/p-rezept-ver/p-rezept-ver.model';
import { PRezeptVerService } from 'app/entities/p-rezept-ver/service/p-rezept-ver.service';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';
import { LieferungService } from 'app/entities/lieferung/service/lieferung.service';

@Component({
  selector: 'jhi-p-rezept-update',
  templateUrl: './p-rezept-update.component.html',
})
export class PRezeptUpdateComponent implements OnInit {
  isSaving = false;

  pStatusesCollection: IPStatus[] = [];
  pRezeptIdsCollection: IPRezeptVer[] = [];
  lieferungsSharedCollection: ILieferung[] = [];

  editForm = this.fb.group({
    id: [],
    pRezeptId: [],
    lieferdat: [],
    lieferungId: [],
    aPeriode: [],
    abDatum: [],
    pStatus: [],
    pRezeptId: [],
    lieferungId: [],
  });

  constructor(
    protected pRezeptService: PRezeptService,
    protected pStatusService: PStatusService,
    protected pRezeptVerService: PRezeptVerService,
    protected lieferungService: LieferungService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezept }) => {
      this.updateForm(pRezept);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pRezept = this.createFromForm();
    if (pRezept.id !== undefined) {
      this.subscribeToSaveResponse(this.pRezeptService.update(pRezept));
    } else {
      this.subscribeToSaveResponse(this.pRezeptService.create(pRezept));
    }
  }

  trackPStatusById(index: number, item: IPStatus): number {
    return item.id!;
  }

  trackPRezeptVerById(index: number, item: IPRezeptVer): number {
    return item.id!;
  }

  trackLieferungById(index: number, item: ILieferung): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPRezept>>): void {
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

  protected updateForm(pRezept: IPRezept): void {
    this.editForm.patchValue({
      id: pRezept.id,
      pRezeptId: pRezept.pRezeptId,
      lieferdat: pRezept.lieferdat,
      lieferungId: pRezept.lieferungId,
      aPeriode: pRezept.aPeriode,
      abDatum: pRezept.abDatum,
      pStatus: pRezept.pStatus,
      pRezeptId: pRezept.pRezeptId,
      lieferungId: pRezept.lieferungId,
    });

    this.pStatusesCollection = this.pStatusService.addPStatusToCollectionIfMissing(this.pStatusesCollection, pRezept.pStatus);
    this.pRezeptIdsCollection = this.pRezeptVerService.addPRezeptVerToCollectionIfMissing(this.pRezeptIdsCollection, pRezept.pRezeptId);
    this.lieferungsSharedCollection = this.lieferungService.addLieferungToCollectionIfMissing(
      this.lieferungsSharedCollection,
      pRezept.lieferungId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pStatusService
      .query({ filter: 'pstatusid-is-null' })
      .pipe(map((res: HttpResponse<IPStatus[]>) => res.body ?? []))
      .pipe(
        map((pStatuses: IPStatus[]) => this.pStatusService.addPStatusToCollectionIfMissing(pStatuses, this.editForm.get('pStatus')!.value))
      )
      .subscribe((pStatuses: IPStatus[]) => (this.pStatusesCollection = pStatuses));

    this.pRezeptVerService
      .query({ filter: 'prezeptid-is-null' })
      .pipe(map((res: HttpResponse<IPRezeptVer[]>) => res.body ?? []))
      .pipe(
        map((pRezeptVers: IPRezeptVer[]) =>
          this.pRezeptVerService.addPRezeptVerToCollectionIfMissing(pRezeptVers, this.editForm.get('pRezeptId')!.value)
        )
      )
      .subscribe((pRezeptVers: IPRezeptVer[]) => (this.pRezeptIdsCollection = pRezeptVers));

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

  protected createFromForm(): IPRezept {
    return {
      ...new PRezept(),
      id: this.editForm.get(['id'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
      lieferdat: this.editForm.get(['lieferdat'])!.value,
      lieferungId: this.editForm.get(['lieferungId'])!.value,
      aPeriode: this.editForm.get(['aPeriode'])!.value,
      abDatum: this.editForm.get(['abDatum'])!.value,
      pStatus: this.editForm.get(['pStatus'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
      lieferungId: this.editForm.get(['lieferungId'])!.value,
    };
  }
}
