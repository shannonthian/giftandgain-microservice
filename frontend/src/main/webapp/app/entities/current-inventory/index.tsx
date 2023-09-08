import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CurrentInventory from './current-inventory';
import CurrentInventoryDetail from './current-inventory-detail';
import CurrentInventoryUpdate from './current-inventory-update';
import CurrentInventoryDeleteDialog from './current-inventory-delete-dialog';

const CurrentInventoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CurrentInventory />} />
    <Route path="new" element={<CurrentInventoryUpdate />} />
    <Route path=":id">
      <Route index element={<CurrentInventoryDetail />} />
      <Route path="edit" element={<CurrentInventoryUpdate />} />
      <Route path="delete" element={<CurrentInventoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CurrentInventoryRoutes;
