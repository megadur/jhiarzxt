import { IERezept } from 'app/entities/e-rezept/e-rezept.model';

export interface IEStatus {
  id?: number;
  wert?: string | null;
  beschreibung?: string | null;
  eStatusId?: IERezept | null;
}

export class EStatus implements IEStatus {
  constructor(public id?: number, public wert?: string | null, public beschreibung?: string | null, public eStatusId?: IERezept | null) {}
}

export function getEStatusIdentifier(eStatus: IEStatus): number | undefined {
  return eStatus.id;
}
