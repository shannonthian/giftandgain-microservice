import axios from "axios";
import { createAsyncThunk, createSlice, isPending, isRejected } from "@reduxjs/toolkit";
import { serializeAxiosError } from "app/shared/reducers/reducer.utils";

export interface IReport {
  month?: number;
  year?: number;
  data?: string;
}

const initialState = {
  loading: false,
  errorMessage: null as unknown as string, // Errors returned from server side
  reports: [] as IReport[],
  reportStr: "",
};

export type ReportState = Readonly<typeof initialState>;

interface IReportParams {
  month: number;
  year: number;
}

const apiUrl = 'api/report';

export const getReports = createAsyncThunk(
  'report/getReports',
  async () => axios.get<any>(`${apiUrl}`),
  {
    serializeError: serializeAxiosError,
  }
);

export const downloadReport = createAsyncThunk(
  'report/downloadReport',
  async (data: IReportParams) => axios.get<any>(`${apiUrl}/download?month=${data.month < 10 ? '0' : ''}${data.month}&year=${data.year}`),
  {
    serializeError: serializeAxiosError,
  }
);

export const ReportSlice = createSlice({
  name: 'report',
  initialState: initialState as ReportState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getReports.fulfilled, (state, action) => ({
        ...state,
        loading: false,
        reports: action.payload.data.payload,
      }))
      .addCase(downloadReport.fulfilled, (state, action) => ({
        ...state,
        loading: false,
        reportStr: action.payload.data.payload[0],
      }))
      .addMatcher(isPending(getReports, downloadReport), (state) => ({
        ...state,
        loading: true,
        errorMessage: null,
      }))
      .addMatcher(isRejected(getReports, downloadReport), (state, action) => ({
        ...state,
        loading: false,
        errorMessage: action.error.message,
      }));
  },
});

export default ReportSlice.reducer;
