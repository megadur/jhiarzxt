import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMuster16, Muster16 } from '../muster-16.model';
import { Muster16Service } from '../service/muster-16.service';
import { IM16Status } from 'app/entities/m-16-status/m-16-status.model';
import { M16StatusService } from 'app/entities/m-16-status/service/m-16-status.service';
import { IMuster16Ver } from 'app/entities/muster-16-ver/muster-16-ver.model';
import { Muster16VerService } from 'app/entities/muster-16-ver/service/muster-16-ver.service';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';
import { LieferungService } from 'app/entities/lieferung/service/lieferung.service';

@Component({
  selector: 'jhi-muster-16-update',
  templateUrl: './muster-16-update.component.html',
})
export class Muster16UpdateComponent implements OnInit {
  isSaving = false;

  m16StatusesCollection: IM16Status[] = [];
  mRezeptIdsCollection: IMuster16Ver[] = [];
  lieferungsSharedCollection: ILieferung[] = [];

  editForm = this.fb.group({
    id: [],
    mRezeptId: [],
    lieferungId: [],
    m16Status: [],
    mMuster16Id: [],
    apoIkSend: [],
    apoIkSnd: [],
    m16Status: [],
    mRezeptId: [],
    lieferungId: [],
  });

  constructor(
    protected muster16Service: Muster16Service,
    protected m16StatusService: M16StatusService,
    protected muster16VerService: Muster16VerService,
    protected lieferungService: LieferungService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16 }) => {
      this.updateForm(muster16);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const muster16 = this.createFromForm();
    if (muster16.id !== undefined) {
      this.subscribeToSaveResponse(this.muster16Service.update(muster16));
    } else {
      this.subscribeToSaveResponse(this.muster16Service.create(muster16));
    }
  }

  trackM16StatusById(index: number, item: IM16Status): number {
    return item.id!;
  }

  trackMuster16VerById(index: number, item: IMuster16Ver): number {
    return item.id!;
  }

  trackLieferungById(index: number, item: ILieferung): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMuster16>>): void {
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

  protected updateForm(muster16: IMuster16): void {
    this.editForm.patchValue({
      id: muster16.id,
      mRezeptId: muster16.mRezeptId,
      lieferungId: muster16.lieferungId,
      m16Status: muster16.m16Status,
      mMuster16Id: muster16.mMuster16Id,
      apoIkSend: muster16.apoIkSend,
      apoIkSnd: muster16.apoIkSnd,
      m16Status: muster16.m16Status,
      mRezeptId: muster16.mRezeptId,
      lieferungId: muster16.lieferungId,
    });

    this.m16StatusesCollection = this.m16StatusService.addM16StatusToCollectionIfMissing(this.m16StatusesCollection, muster16.m16Status);
    this.mRezeptIdsCollection = this.muster16VerService.addMuster16VerToCollectionIfMissing(this.mRezeptIdsCollection, muster16.mRezeptId);
    this.lieferungsSharedCollection = this.lieferungService.addLieferungToCollectionIfMissing(
      this.lieferungsSharedCollection,
      muster16.lieferungId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.m16StatusService
      .query({ filter: 'm16statusid-is-null' })
      .pipe(map((res: HttpResponse<IM16Status[]>) => res.body ?? []))
      .pipe(
        map((m16Statuses: IM16Status[]) =>
          this.m16StatusService.addM16StatusToCollectionIfMissing(m16Statuses, this.editForm.get('m16Status')!.value)
        )
      )
      .subscribe((m16Statuses: IM16Status[]) => (this.m16StatusesCollection = m16Statuses));

    this.muster16VerService
      .query({ filter: 'mrezeptid-is-null' })
      .pipe(map((res: HttpResponse<IMuster16Ver[]>) => res.body ?? []))
      .pipe(
        map((muster16Vers: IMuster16Ver[]) =>
          this.muster16VerService.addMuster16VerToCollectionIfMissing(muster16Vers, this.editForm.get('mRezeptId')!.value)
        )
      )
      .subscribe((muster16Vers: IMuster16Ver[]) => (this.mRezeptIdsCollection = muster16Vers));

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

  protected createFromForm(): IMuster16 {
    return {
      ...new Muster16(),
      id: this.editForm.get(['id'])!.value,
      mRezeptId: this.editForm.get(['mRezeptId'])!.value,
      lieferungId: this.editForm.get(['lieferungId'])!.value,
      m16Status: this.editForm.get(['m16Status'])!.value,
      mMuster16Id: this.editForm.get(['mMuster16Id'])!.value,
      apoIkSend: this.editForm.get(['apoIkSend'])!.value,
      apoIkSnd: this.editForm.get(['apoIkSnd'])!.value,
      m16Status: this.editForm.get(['m16Status'])!.value,
      mRezeptId: this.editForm.get(['mRezeptId'])!.value,
      lieferungId: this.editForm.get(['lieferungId'])!.value,
    };
  }
}
