# Entities at a glance

## Inventory

{

category:

name:

quantity:

unit:

expiry_date: date

location:

created_date: dateTime

}

## Target inventory

{

category:

quantity:

validity: integer (in days)

}

# Query

| **API**                          | **Method** | **Request Body** | **Response Body**                                                             | **Remarks**                                                                                                                                                                                                                                                                   |
| -------------------------------- | ---------- | ---------------- | ----------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **/api/query/inventory**         | GET        |
| { payload: […inventory]}         |
|                                  |
| **/api/query/target-quantities** | GET        |
| { payload: […targetInventory]}   |
|                                  |
| **/api/query/wish-list**         | GET        | -                | {"high": ["Item A", "Item B", "Item C"],"low": ["Item D", "Item E","Item F"]} | "high" would contain the list of items where current_inventory is less than target_inventory, ordered from largest to smallest difference;"low" would contain the list of items where current_inventory exceeds target_inventory, ordered from smallest to largest difference |

# Auth

| **API**                                                                                                                                                                                                                                                                                                | **Method** | **Request Body**                                                                                 | **Response Body**                                                                                                      | **Remarks**                                            |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------- | ------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------ |
| **/api/auth/register** **(from /api/register)**                                                                                                                                                                                                                                                        | POST       | { "login": "username", "email: "test@test.com","password": "password",}                          |
| Should return error if account has been activated (based on email). Should return error if account is not in the list of approved emails. User's role is based on email (i.e., only certain emails will be assigned ROLE_MANGER). username is already in use password is not strong enoughempty fields |
| **/api/auth/login\*\*** (from /api/authenticate)\*\*                                                                                                                                                                                                                                                   | POST       | { "username": "username", "password": "password",}                                               | {}                                                                                                                     | To return bearer token (i.e., JWT) in response header. |
| **/api/auth/logout\*\*** (from /api/authenticate)\*\*                                                                                                                                                                                                                                                  | POST       | { "username": "username", "password": "password",}                                               |
|                                                                                                                                                                                                                                                                                                        |
|                                                                                                                                                                                                                                                                                                        |
| **/api/auth/profile/:id\*\*** (from /api/account)\*\*                                                                                                                                                                                                                                                  | GET        | -                                                                                                | { "id": 1, "login": "username", "firstname": "TEST", "lastname": "TEST", "email": "test@test.com","role": "ROLE_USER", | Refer to interface IUser in user.model.ts.             |
| **/api/auth/profile/:id\*\*** (from /api/account)\*\*                                                                                                                                                                                                                                                  | POST       | { "username": "username", "email": "test@test.com", "role": "ROLE_USER", "password": "password"} | {}                                                                                                                     | Refer to interface IUser in user.model.ts.             |

# Inventory

| **API**                    | **Method**                                                | **Request Body**         | **Response Body** | **Remarks** |
| -------------------------- | --------------------------------------------------------- | ------------------------ | ----------------- | ----------- |
| **/api/inventory/current** | GET                                                       |
| { payload: […inventory]}   | Same as GET /api/query/inventory without query parameters |
| **/api/inventory/current** | POST                                                      | { Payload: […inventory]} |
|                            |
|                            |

# Database diagram (ERD for relational database)

https://databasediagram.com/app
tbl_user

- ID int PK
  username varchar(100) NOT NULL
  password varchar(50) NOT NULL
  email varchar(100) NOT NULL
  roleID int FK > tbl_code_role.role PK

## tbl_code_role

ID int PK
role varchar(20) NOT NULL
description varchar(100) NOT NULL

## tbl_code_unit

ID int PK
unit varchar(20)
description varchar(100)

## tbl_code_category

ID int PK
unit varchar(20)
description varchar(100)
