export const AUTHORITIES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
  MANAGER: 'ROLE_MANAGER',
};

export const STATUS_LIST = [
  {
    code: 'A',
    desc: 'Active',
  },
  {
    code: 'I',
    desc: 'Inactive',
  },
];
export const getStatusDesc = (statusCode: string) => STATUS_LIST.find(item => item.code === statusCode)?.desc;

export const messages = {
  DATA_ERROR_ALERT: 'Internal Error',
};

export const APP_DATE_FORMAT = 'DD/MM/YY HH:mm';
export const APP_TIMESTAMP_FORMAT = 'DD/MM/YY HH:mm:ss';
export const APP_LOCAL_DATE_FORMAT = 'DD/MM/YYYY';
export const APP_LOCAL_DATETIME_FORMAT = 'YYYY-MM-DDTHH:mm';
export const APP_WHOLE_NUMBER_FORMAT = '0,0';
export const APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT = '0,0.[00]';

export const DATE_FORMAT = 'YYYY-MM-DD';
export const DATE_DISPLAY_FORMAT = 'MMMM YYYY';

export const AUTH_MICROSERVICE = 'http://localhost:16668';
export const API_REGISTER = AUTH_MICROSERVICE + '/api/register';
export const API_UPDATE_ACCOUNT = AUTH_MICROSERVICE + '/api/account';
export const API_ACTIVATE_ACCOUNT = (key: string) => `${AUTH_MICROSERVICE}/api/activate?key=${key}`;
export const API_CHANGE_PASSWORD = AUTH_MICROSERVICE + '/api/change-password';
export const API_LOGIN = AUTH_MICROSERVICE + '/api/login';
export const API_ACCOUNT = AUTH_MICROSERVICE + '/api/account';
