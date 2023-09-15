# Query

| **API**   | **Method**  | **Request Body**  | **Response Body** | **Remarks**  |
| --- | ----------- | --- | --- | --- |
| **/api/query/inventory**         | GET         |                   | { payload: […inventory]}                                                       |
| **/api/query/target-quantities** | GET         |                   | { payload: […targetInventory]}                                                 |                                                                                                                                                                                                                                                                               |     |
| **/api/query/wish-list**         | GET         | -                 | {"high": ["Item A", "Item B", "Item C"],"low": ["Item D", "Item E","Item F"]}  | "high" would contain the list of items where current_inventory is less than target_inventory, ordered from largest to smallest difference;"low" would contain the list of items where current_inventory exceeds target_inventory, ordered from smallest to largest difference |

# Authentication

| **API** | **Method**  | **Request Body**  | **Response Body**  | **Remarks** |
| --- | --- | --- | --- | --- |
| /api/auth/register      | POST        | { "login": "username", "email: "test@test.com","password": "password",}                           |                                                                                                                         | Should return error if account has been activated (based on email). Should return error if account is not in the list of approved emails. User's role is based on email (i.e., only certain emails will be assigned ROLE_MANGER). username is already in use password is not strong enoughempty fields  |
| /api/auth/login         | POST        | { "username": "username", "password": "password",}                                                | {}                                                                                                                      | To return bearer token (i.e., JWT) in response header.                                                                                                                                                                                                                                                  |
| /api/auth/logout        | POST        | { "username": "username", "password": "password",}                                                |                                                                                                                         |                                                                                                                                                                                                                                                                                                         |     |     |
| api/auth/profile/:id    | GET         | -                                                                                                 | { "id": 1, "login": "username", "firstname": "TEST", "lastname": "TEST", "email": "test@test.com","role": "ROLE_USER",  | Refer to interface IUser in user.model.ts.                                                                                                                                                                                                                                                              |
| /api/auth/profile/:id\  | POST        | { "username": "username", "email": "test@test.com", "role": "ROLE_USER", "password": "password"}  | {}                                                                                                                      | Refer to interface IUser in user.model.ts.                                                                                                                                                                                                                                                              |

# Inventory

| **API**   | **Method** | **Request Body**  | **Response Body**  |**Remarks**                                               |
| ---- | --- | ---- | --- | --- |
| api/inventory/current     | GET        |                                | { payload: […inventory]}       | Same as GET /api/query/inventory without query parameters |
| api/inventory/current     | POST       | { payload: […inventory]}       |                                |                                                           |
| api/inventory/current/:id | GET        |                                | { payload: […inventory]}       |                                                           |
| api/inventory/current/:id | PUT        | { payload: […inventory]}       | { payload: inventory.id}       |                                                           |
| api/inventory/current/:id | DELETE     |                                | { payload: inventory.id}       |                                                           |
| /api/inventory/target     | GET        |                                | { payload: […targetInventory]} |                                                           |
| /api/inventory/target     | PUT        | { payload: […targetInventory]} | { payload: inventory.id}       |                                                           |
