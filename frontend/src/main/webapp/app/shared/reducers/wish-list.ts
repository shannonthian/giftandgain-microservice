import axios from "axios";
import { createAsyncThunk, createSlice, isPending, isRejected } from "@reduxjs/toolkit";
import { serializeAxiosError } from "./reducer.utils";

const initialState = {
  loading: false,
  errorMessage: null as unknown as string, // Errors returned from server side
  highPriorityWishList: [] as string[],
  lowPriorityWishList: [] as string[],
};

export type WishListState = Readonly<typeof initialState>;

interface IWishListParams {
  month: number;
  year: number;
}

const apiUrl = 'giftandgain/inventory';

export const getHighPriorityWishList = createAsyncThunk(
  'wishList/getHighPriorityWishList',
  async (data: IWishListParams) => axios.get<any>(`${apiUrl}/highpriority/${data.month}/${data.year}`),
  {
    serializeError: serializeAxiosError,
  }
);

export const getLowPriorityWishList = createAsyncThunk(
  'wishList/getLowPriorityWishList',
  async (data: IWishListParams) => axios.get<any>(`${apiUrl}/lowpriority/${data.month}/${data.year}`),
  {
    serializeError: serializeAxiosError,
  }
);

export const WishListSlice = createSlice({
  name: 'wishList',
  initialState: initialState as WishListState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getHighPriorityWishList.fulfilled, (state, action) => ({
        ...state,
        loading: false,
        highPriorityWishList: action.payload.data,
      }))
      .addCase(getLowPriorityWishList.fulfilled, (state, action) => ({
        ...state,
        loading: false,
        lowPriorityWishList: action.payload.data,
      }))
      .addMatcher(isPending(getHighPriorityWishList, getLowPriorityWishList), (state) => ({
        ...state,
        loading: true,
        errorMessage: null,
      }))
      .addMatcher(isRejected(getHighPriorityWishList, getLowPriorityWishList), (state, action) => ({
        ...state,
        loading: false,
        errorMessage: action.error.message,
      }));
  },
});

export default WishListSlice.reducer;
