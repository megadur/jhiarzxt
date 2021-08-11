import { IPStatus } from 'app/entities/p-status/p-status.model';
import { IPRezeptVer } from 'app/entities/p-rezept-ver/p-rezept-ver.model';
import { IPCharge } from 'app/entities/p-charge/p-charge.model';
import { IPStatusInfo } from 'app/entities/p-status-info/p-status-info.model';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';

export interface IPRezept {
  id?: number;
  pRezeptId?: string | null;
  lieferdat?: string | null;
  lieferungId?: string | null;
  aPeriode?: string | null;
  abDatum?: string | null;
  pStatus?: IPStatus | null;
  pRezeptId?: IPRezeptVer | null;
  pCharges?: IPCharge[] | null;
  pStatusInfos?: IPStatusInfo[] | null;
  lieferungId?: ILieferung | null;
}

export class PRezept implements IPRezept {
  constructor(
    public id?: number,
    public pRezeptId?: string | null,
    public lieferdat?: string | null,
    public lieferungId?: string | null,
    public aPeriode?: string | null,
    public abDatum?: string | null,
    public pStatus?: IPStatus | null,
    public pRezeptId?: IPRezeptVer | null,
    public pCharges?: IPCharge[] | null,
    public pStatusInfos?: IPStatusInfo[] | null,
    public lieferungId?: ILieferung | null
  ) {}
}

export function getPRezeptIdentifier(pRezept: IPRezept): number | undefined {
  return pRezept.id;
}
