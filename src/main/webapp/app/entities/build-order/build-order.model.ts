import * as dayjs from 'dayjs';
import { IRequisition } from 'app/entities/requisition/requisition.model';
import { BuildOrderState } from 'app/entities/enumerations/build-order-state.model';

export interface IBuildOrder {
  id?: number;
  state?: BuildOrderState;
  start?: dayjs.Dayjs;
  finish?: dayjs.Dayjs;
  requisition?: IRequisition;
}

export class BuildOrder implements IBuildOrder {
  constructor(
    public id?: number,
    public state?: BuildOrderState,
    public start?: dayjs.Dayjs,
    public finish?: dayjs.Dayjs,
    public requisition?: IRequisition
  ) {}
}
