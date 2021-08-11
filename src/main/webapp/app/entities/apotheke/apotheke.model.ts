import { ILieferung } from 'app/entities/lieferung/lieferung.model';
import { IRechenzentrum } from 'app/entities/rechenzentrum/rechenzentrum.model';

export interface IApotheke {
  id?: number;
  iK?: number | null;
  inhaber?: string | null;
  countryCode?: string | null;
  plz?: string | null;
  ort?: string | null;
  str?: string | null;
  hausNr?: string | null;
  addrZusatz?: string | null;
  lieferungs?: ILieferung[] | null;
  rechenzentrums?: IRechenzentrum[] | null;
}

export class Apotheke implements IApotheke {
  constructor(
    public id?: number,
    public iK?: number | null,
    public inhaber?: string | null,
    public countryCode?: string | null,
    public plz?: string | null,
    public ort?: string | null,
    public str?: string | null,
    public hausNr?: string | null,
    public addrZusatz?: string | null,
    public lieferungs?: ILieferung[] | null,
    public rechenzentrums?: IRechenzentrum[] | null
  ) {}
}

export function getApothekeIdentifier(apotheke: IApotheke): number | undefined {
  return apotheke.id;
}
