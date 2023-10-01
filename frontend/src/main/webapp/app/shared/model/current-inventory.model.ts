import { ICategory } from './category.model';

export interface ICurrentInventory {
  inventoryId?: number;
  itemName?: string;
  category?: ICategory;
  receivedQuantity?: number;
  expiryDate?: string;
  createdBy?: string;
  createdDate?: string;
  remarks?: string | null;
}

export const defaultValue: Readonly<ICurrentInventory> = {};
