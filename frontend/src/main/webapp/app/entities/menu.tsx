import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="boxes-stacked" to="/current-inventory">
        <Translate contentKey="global.menu.entities.currentInventory" />
      </MenuItem>
      <MenuItem icon="bullseye" to="/target-inventory">
        <Translate contentKey="global.menu.entities.targetInventory" />
      </MenuItem>
      <MenuItem icon="layer-group" to="/category">
        <Translate contentKey="global.menu.entities.category" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
