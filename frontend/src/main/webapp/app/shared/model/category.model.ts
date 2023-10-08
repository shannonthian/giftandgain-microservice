export interface ICategory {
  categoryId?: number;
  category?: string;
  unit?: string;
  status?: string;
}

export const defaultValue: Readonly<ICategory> = {};
