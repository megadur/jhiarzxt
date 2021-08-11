import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';

export interface IPStatus {
  id?: number;
  wert?: string | null;
  beschreibung?: string | null;
  pStatusId?: IPRezept | null;
}

export class PStatus implements IPStatus {
  constructor(public id?: number, public wert?: string | null, public beschreibung?: string | null, public pStatusId?: IPRezept | null) {}
}

export function getPStatusIdentifier(pStatus: IPStatus): number | undefined {
  return pStatus.id;
}
