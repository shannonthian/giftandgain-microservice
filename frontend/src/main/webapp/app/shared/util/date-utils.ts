import dayjs from 'dayjs';

import { APP_LOCAL_DATETIME_FORMAT, DATE_DISPLAY_FORMAT } from 'app/config/constants';

export const convertDateTimeFromServer = date => (date ? dayjs(date).format(APP_LOCAL_DATETIME_FORMAT) : null);

export const convertDateTimeToServer = date => (date ? dayjs(date).toDate() : null);

export const displayDefaultDateTime = () => dayjs().startOf('day').format(APP_LOCAL_DATETIME_FORMAT);

export const convertDateToDateDisplay = (dateStr: string) => dayjs(new Date(dateStr)).format(DATE_DISPLAY_FORMAT);

export const addMonth = (startDate: number, noOfMonths: number, dateFormat: string) => {
  const date = new Date();
  if (startDate) {
    date.setDate(startDate);
  }
  date.setMonth(date.getMonth() + noOfMonths);
  const dateStr = dayjs(date).format(dateFormat);
  return dateStr;
};
