import axios from "axios";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { serializeAxiosError } from "./reducer.utils";

export interface IUser {
  id?: number;
  name?: string;
  username?: string;
  password?: string;
  email?: string;
  authorities?: string[];
}

const initialState = {
  loading: false,
  errorMessage: null as unknown as string, // Errors returned from server side
  users: [] as IUser[],
};

export type UserState = Readonly<typeof initialState>;

const apiUrl = 'api/users';

export const getUsers = createAsyncThunk(
  'user/getUsers',
  async () => axios.get<IUser[]>(`${apiUrl}`),
  {
    serializeError: serializeAxiosError,
  }
);

export const UserSlice = createSlice({
  name: 'user',
  initialState: initialState as UserState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getUsers.fulfilled, (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          users: data.sort((a, b) => {
            return a.username < b.username ? -1 : 1;
          }),
        }
      })
      .addCase(getUsers.pending, (state) => ({
        ...state,
        loading: true,
        errorMessage: null,
      }))
      .addCase(getUsers.rejected, (state, action) => ({
        ...state,
        loading: false,
        errorMessage: action.error.message,
      }));
  },
});

export default UserSlice.reducer;
