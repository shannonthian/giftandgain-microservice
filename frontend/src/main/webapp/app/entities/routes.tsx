import React from 'react';
import { Route } from 'react-router-dom';

import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CurrentInventory from './current-inventory';
import TargetInventory from './target-inventory';
import Category from './category';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="current-inventory/*" element={<CurrentInventory />} />
        <Route
          path="target-inventory/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.MANAGER]}>
              <TargetInventory />
            </PrivateRoute>
          }
        />
        <Route
          path="category/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.MANAGER]}>
              <Category />
            </PrivateRoute>
          }
        />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
