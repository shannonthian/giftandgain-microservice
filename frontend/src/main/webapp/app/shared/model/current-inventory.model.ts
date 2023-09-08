import dayjs from 'dayjs';

export interface ICurrentInventory {
  id?: number;
  itemName?: string;
  expiryDate?: string;
  quantity?: number;
}

export const defaultValue: Readonly<ICurrentInventory> = {};
