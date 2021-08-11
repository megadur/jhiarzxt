import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPStatus, PStatus } from '../p-status.model';
import { PStatusService } from '../service/p-status.service';

@Component({
  selector: 'jhi-p-status-update',
  templateUrl: './p-status-update.component.html',
})
export class PStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    wert: [],
    beschreibung: [],
  });

  constructor(protected pStatusService: PStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pStatus }) => {
      this.updateForm(pStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pStatus = this.createFromForm();
    if (pStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.pStatusService.update(pStatus));
    } else {
      this.subscribeToSaveResponse(this.pStatusService.create(pStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPStatus>>): void {
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

  protected updateForm(pStatus: IPStatus): void {
    this.editForm.patchValue({
      id: pStatus.id,
      wert: pStatus.wert,
      beschreibung: pStatus.beschreibung,
    });
  }

  protected createFromForm(): IPStatus {
    return {
      ...new PStatus(),
      id: this.editForm.get(['id'])!.value,
      wert: this.editForm.get(['wert'])!.value,
      beschreibung: this.editForm.get(['beschreibung'])!.value,
    };
  }
}
