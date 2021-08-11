import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEStatus, EStatus } from '../e-status.model';
import { EStatusService } from '../service/e-status.service';

@Component({
  selector: 'jhi-e-status-update',
  templateUrl: './e-status-update.component.html',
})
export class EStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    wert: [],
    beschreibung: [],
  });

  constructor(protected eStatusService: EStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eStatus }) => {
      this.updateForm(eStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eStatus = this.createFromForm();
    if (eStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.eStatusService.update(eStatus));
    } else {
      this.subscribeToSaveResponse(this.eStatusService.create(eStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEStatus>>): void {
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

  protected updateForm(eStatus: IEStatus): void {
    this.editForm.patchValue({
      id: eStatus.id,
      wert: eStatus.wert,
      beschreibung: eStatus.beschreibung,
    });
  }

  protected createFromForm(): IEStatus {
    return {
      ...new EStatus(),
      id: this.editForm.get(['id'])!.value,
      wert: this.editForm.get(['wert'])!.value,
      beschreibung: this.editForm.get(['beschreibung'])!.value,
    };
  }
}
