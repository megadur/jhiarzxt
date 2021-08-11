import * as dayjs from 'dayjs';
import { IApotheke } from 'app/entities/apotheke/apotheke.model';

export interface ILieferung {
  id?: number;
  iD?: string | null;
  datum?: dayjs.Dayjs | null;
  apoIk?: string | null;
  apotheke?: IApotheke | null;
}

export class Lieferung implements ILieferung {
  constructor(
    public id?: number,
    public iD?: string | null,
    public datum?: dayjs.Dayjs | null,
    public apoIk?: string | null,
    public apotheke?: IApotheke | null
  ) {}
}

export function getLieferungIdentifier(lieferung: ILieferung): number | undefined {
  return lieferung.id;
}
