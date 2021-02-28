import * as dayjs from 'dayjs';

export interface ICitadel {
  id?: number;
  name?: string;
  address?: string;
  start?: dayjs.Dayjs;
  finish?: dayjs.Dayjs;
}

export class Citadel implements ICitadel {
  constructor(public id?: number, public name?: string, public address?: string, public start?: dayjs.Dayjs, public finish?: dayjs.Dayjs) {}
}
