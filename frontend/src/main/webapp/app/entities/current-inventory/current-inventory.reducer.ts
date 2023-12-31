import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { ICurrentInventory, defaultValue } from 'app/shared/model/current-inventory.model';

interface ISearchParams extends IQueryParams {
  itemName?: string;
  categoryId?: number;
  expiryStartDateStr?: string;
  expiryEndDateStr?: string;
  createdBy?: string;
  createdStartDateStr?: string;
  createdEndDateStr?: string;
}

const initialState: EntityState<ICurrentInventory> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'giftandgain/inventory';

// Actions

export const getEntities = createAsyncThunk('currentInventory/fetch_entity_list',
  async ({ itemName, categoryId, expiryStartDateStr, expiryEndDateStr, createdBy, createdStartDateStr, createdEndDateStr, page, size, sort }: ISearchParams) => {
    const sortSplit = sort?.split(',');
    const requestUrl = `${apiUrl}/search?` +
      `${itemName ? `itemName=${itemName}&` : ''}` +
      `${categoryId ? `categoryId=${categoryId}&` : ''}` +
      `${expiryStartDateStr ? `expiryStartDateStr=${expiryStartDateStr}&` : ''}` +
      `${expiryEndDateStr ? `expiryEndDateStr=${expiryEndDateStr}&` : ''}` +
      `${createdBy ? `createdBy=${createdBy}&` : ''}` +
      `${createdStartDateStr ? `createdStartDateStr=${createdStartDateStr}&` : ''}` +
      `${createdEndDateStr ? `createdEndDateStr=${createdEndDateStr}&` : ''}` +
      `${sort ? `page=${page}&size=${size}&sort=${sortSplit[0]}&direction=${sortSplit[1]}` : ''}`;
    return axios.get<ICurrentInventory[]>(requestUrl);
  });

export const getEntity = createAsyncThunk(
  'currentInventory/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ICurrentInventory>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'currentInventory/create_entity',
  async (entity: ICurrentInventory, thunkAPI) => {
    const result = await axios.post<ICurrentInventory>(`${apiUrl}/create`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'currentInventory/update_entity',
  async (entity: ICurrentInventory, thunkAPI) => {
    const result = await axios.put<ICurrentInventory>(`${apiUrl}/edit/${entity.inventoryId}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'currentInventory/partial_update_entity',
  async (entity: ICurrentInventory, thunkAPI) => {
    const result = await axios.patch<ICurrentInventory>(`${apiUrl}/edit/${entity.inventoryId}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'currentInventory/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/delete/${id}`;
    const result = await axios.delete<ICurrentInventory>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const CurrentInventorySlice = createEntitySlice({
  name: 'currentInventory',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = CurrentInventorySlice.actions;

// Reducer
export default CurrentInventorySlice.reducer;
