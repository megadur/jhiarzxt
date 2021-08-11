import { IMuster16 } from 'app/entities/muster-16/muster-16.model';

export interface IMArtikel {
  id?: number;
  mArtikelId?: string | null;
  mRezeptId?: string | null;
  apoIk?: string | null;
  autidem?: string | null;
  faktor?: string | null;
  hilfsmittelNr?: string | null;
  muster16Id?: string | null;
  posNr?: string | null;
  pzn?: string | null;
  taxe?: string | null;
  vZeile?: string | null;
  muster16?: IMuster16 | null;
}

export class MArtikel implements IMArtikel {
  constructor(
    public id?: number,
    public mArtikelId?: string | null,
    public mRezeptId?: string | null,
    public apoIk?: string | null,
    public autidem?: string | null,
    public faktor?: string | null,
    public hilfsmittelNr?: string | null,
    public muster16Id?: string | null,
    public posNr?: string | null,
    public pzn?: string | null,
    public taxe?: string | null,
    public vZeile?: string | null,
    public muster16?: IMuster16 | null
  ) {}
}

export function getMArtikelIdentifier(mArtikel: IMArtikel): number | undefined {
  return mArtikel.id;
}
