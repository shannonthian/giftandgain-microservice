import currentInventory from 'app/entities/current-inventory/current-inventory.reducer';
import targetInventory from 'app/entities/target-inventory/target-inventory.reducer';
import reportDownload from './target-inventory/target-inventory-report-download.reducer';
import category from 'app/entities/category/category.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  currentInventory,
  targetInventory,
  reportDownload,
  category,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
