import { IPCharge } from 'app/entities/p-charge/p-charge.model';

export interface IPWirkstoff {
  id?: number;
  pWirkstoffId?: string | null;
  pChargeId?: string | null;
  apoIk?: string | null;
  faktor?: string | null;
  faktorKennzeichen?: string | null;
  notiz?: string | null;
  pPosNr?: string | null;
  preisKennzeichen?: string | null;
  pzn?: string | null;
  taxe?: string | null;
  wirkstoffName?: string | null;
  pCharge?: IPCharge | null;
}

export class PWirkstoff implements IPWirkstoff {
  constructor(
    public id?: number,
    public pWirkstoffId?: string | null,
    public pChargeId?: string | null,
    public apoIk?: string | null,
    public faktor?: string | null,
    public faktorKennzeichen?: string | null,
    public notiz?: string | null,
    public pPosNr?: string | null,
    public preisKennzeichen?: string | null,
    public pzn?: string | null,
    public taxe?: string | null,
    public wirkstoffName?: string | null,
    public pCharge?: IPCharge | null
  ) {}
}

export function getPWirkstoffIdentifier(pWirkstoff: IPWirkstoff): number | undefined {
  return pWirkstoff.id;
}
