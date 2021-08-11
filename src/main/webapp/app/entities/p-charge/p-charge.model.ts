import { IPWirkstoff } from 'app/entities/p-wirkstoff/p-wirkstoff.model';
import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';

export interface IPCharge {
  id?: number;
  pChargeId?: string | null;
  pRezeptId?: string | null;
  anzahlApplikationen?: string | null;
  apoIk?: string | null;
  chargenNr?: string | null;
  herstellerNr?: string | null;
  herstellerSchluessel?: string | null;
  herstellungsDatum?: string | null;
  pWirkstoffs?: IPWirkstoff[] | null;
  pRezept?: IPRezept | null;
}

export class PCharge implements IPCharge {
  constructor(
    public id?: number,
    public pChargeId?: string | null,
    public pRezeptId?: string | null,
    public anzahlApplikationen?: string | null,
    public apoIk?: string | null,
    public chargenNr?: string | null,
    public herstellerNr?: string | null,
    public herstellerSchluessel?: string | null,
    public herstellungsDatum?: string | null,
    public pWirkstoffs?: IPWirkstoff[] | null,
    public pRezept?: IPRezept | null
  ) {}
}

export function getPChargeIdentifier(pCharge: IPCharge): number | undefined {
  return pCharge.id;
}
