import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TargetInventory from './target-inventory';
import TargetInventoryDetail from './target-inventory-detail';
import TargetInventoryUpdate from './target-inventory-update';
import TargetInventoryDeleteDialog from './target-inventory-delete-dialog';
import TargetInventoryReportDownload from './target-inventory-report-download';

const TargetInventoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TargetInventory />} />
    <Route path="new" element={<TargetInventoryUpdate />} />
    <Route path="report-download" element={<TargetInventoryReportDownload />} />
    <Route path=":id">
      <Route index element={<TargetInventoryDetail />} />
      <Route path="edit" element={<TargetInventoryUpdate />} />
      <Route path="delete" element={<TargetInventoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TargetInventoryRoutes;
