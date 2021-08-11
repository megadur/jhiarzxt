import * as dayjs from 'dayjs';
import { IEStatus } from 'app/entities/e-status/e-status.model';
import { IEAenderung } from 'app/entities/e-aenderung/e-aenderung.model';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';

export interface IERezept {
  id?: number;
  iD?: string | null;
  dokVer?: string | null;
  abgInfo?: string | null;
  abgDatum?: dayjs.Dayjs | null;
  abgGesZuzahl?: number | null;
  abgGesBrutto?: number | null;
  abgVertragskz?: string | null;
  eStatus?: IEStatus | null;
  eAenderungs?: IEAenderung[] | null;
  lieferungId?: ILieferung | null;
}

export class ERezept implements IERezept {
  constructor(
    public id?: number,
    public iD?: string | null,
    public dokVer?: string | null,
    public abgInfo?: string | null,
    public abgDatum?: dayjs.Dayjs | null,
    public abgGesZuzahl?: number | null,
    public abgGesBrutto?: number | null,
    public abgVertragskz?: string | null,
    public eStatus?: IEStatus | null,
    public eAenderungs?: IEAenderung[] | null,
    public lieferungId?: ILieferung | null
  ) {}
}

export function getERezeptIdentifier(eRezept: IERezept): number | undefined {
  return eRezept.id;
}
