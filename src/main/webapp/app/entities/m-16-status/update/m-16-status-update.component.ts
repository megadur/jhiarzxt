import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IM16Status, M16Status } from '../m-16-status.model';
import { M16StatusService } from '../service/m-16-status.service';

@Component({
  selector: 'jhi-m-16-status-update',
  templateUrl: './m-16-status-update.component.html',
})
export class M16StatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    m16StatusId: [],
    status: [],
  });

  constructor(protected m16StatusService: M16StatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ m16Status }) => {
      this.updateForm(m16Status);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const m16Status = this.createFromForm();
    if (m16Status.id !== undefined) {
      this.subscribeToSaveResponse(this.m16StatusService.update(m16Status));
    } else {
      this.subscribeToSaveResponse(this.m16StatusService.create(m16Status));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IM16Status>>): void {
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

  protected updateForm(m16Status: IM16Status): void {
    this.editForm.patchValue({
      id: m16Status.id,
      m16StatusId: m16Status.m16StatusId,
      status: m16Status.status,
    });
  }

  protected createFromForm(): IM16Status {
    return {
      ...new M16Status(),
      id: this.editForm.get(['id'])!.value,
      m16StatusId: this.editForm.get(['m16StatusId'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
