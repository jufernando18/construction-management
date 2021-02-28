import * as dayjs from 'dayjs';
import { IBuildType } from 'app/entities/build-type/build-type.model';
import { ICitadel } from 'app/entities/citadel/citadel.model';
import { RequisitionState } from 'app/entities/enumerations/requisition-state.model';

export interface IRequisition {
  id?: number;
  name?: string;
  coordinate?: string;
  state?: RequisitionState;
  date?: dayjs.Dayjs;
  buildType?: IBuildType;
  citadel?: ICitadel;
}

export class Requisition implements IRequisition {
  constructor(
    public id?: number,
    public name?: string,
    public coordinate?: string,
    public state?: RequisitionState,
    public date?: dayjs.Dayjs,
    public buildType?: IBuildType,
    public citadel?: ICitadel
  ) {}
}
