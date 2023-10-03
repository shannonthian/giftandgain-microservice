import React from 'react';
import { Route } from 'react-router-dom';

import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CurrentInventory from './current-inventory';
import CurrentInventoryDetail from './current-inventory-detail';
import CurrentInventoryUpdate from './current-inventory-update';
import CurrentInventoryDeleteDialog from './current-inventory-delete-dialog';

const CurrentInventoryRoutes = ({ isManager }) => (
  <ErrorBoundaryRoutes>
    <Route index element={<CurrentInventory isManager={isManager} />} />
    <Route path="new" element={<CurrentInventoryUpdate />} />
    <Route path=":id">
      <Route index element={<CurrentInventoryDetail isManager={isManager} />} />
      <Route path="edit" element={
        <PrivateRoute hasAnyAuthorities={[AUTHORITIES.MANAGER]}>
          <CurrentInventoryUpdate />
        </PrivateRoute>
      } />
      <Route path="delete" element={
        <PrivateRoute hasAnyAuthorities={[AUTHORITIES.MANAGER]}>
          <CurrentInventoryDeleteDialog />
        </PrivateRoute>
      } />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CurrentInventoryRoutes;
