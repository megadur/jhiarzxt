import { IPRezeptAbg } from 'app/entities/p-rezept-abg/p-rezept-abg.model';

export interface IPRezeptAbr {
  id?: number;
  pRezeptId?: string | null;
  betragRabA?: string | null;
  betragRabH?: string | null;
  betragApoAusz?: string | null;
  pRezeptId?: IPRezeptAbg | null;
}

export class PRezeptAbr implements IPRezeptAbr {
  constructor(
    public id?: number,
    public pRezeptId?: string | null,
    public betragRabA?: string | null,
    public betragRabH?: string | null,
    public betragApoAusz?: string | null,
    public pRezeptId?: IPRezeptAbg | null
  ) {}
}

export function getPRezeptAbrIdentifier(pRezeptAbr: IPRezeptAbr): number | undefined {
  return pRezeptAbr.id;
}
