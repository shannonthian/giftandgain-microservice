import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('TargetInventory e2e test', () => {
  const targetInventoryPageUrl = '/target-inventory';
  const targetInventoryPageUrlPattern = new RegExp('/target-inventory(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const targetInventorySample = { targetMonthYear: 'Southeast intangible', category: 8462, targetQuantity: 6130, unit: 'Oganesson huzzah' };

  let targetInventory;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/target-inventories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/target-inventories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/target-inventories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (targetInventory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/target-inventories/${targetInventory.id}`,
      }).then(() => {
        targetInventory = undefined;
      });
    }
  });

  it('TargetInventories menu should load TargetInventories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('target-inventory');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TargetInventory').should('exist');
    cy.url().should('match', targetInventoryPageUrlPattern);
  });

  describe('TargetInventory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(targetInventoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TargetInventory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/target-inventory/new$'));
        cy.getEntityCreateUpdateHeading('TargetInventory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetInventoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/target-inventories',
          body: targetInventorySample,
        }).then(({ body }) => {
          targetInventory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/target-inventories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [targetInventory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(targetInventoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TargetInventory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('targetInventory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetInventoryPageUrlPattern);
      });

      it('edit button click should load edit TargetInventory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TargetInventory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetInventoryPageUrlPattern);
      });

      it('edit button click should load edit TargetInventory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TargetInventory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetInventoryPageUrlPattern);
      });

      it('last delete button click should delete instance of TargetInventory', () => {
        cy.intercept('GET', '/api/target-inventories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('targetInventory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetInventoryPageUrlPattern);

        targetInventory = undefined;
      });
    });
  });

  describe('new TargetInventory page', () => {
    beforeEach(() => {
      cy.visit(`${targetInventoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TargetInventory');
    });

    it('should create an instance of TargetInventory', () => {
      cy.get(`[data-cy="targetMonthYear"]`).type('Steel Principal Granite');
      cy.get(`[data-cy="targetMonthYear"]`).should('have.value', 'Steel Principal Granite');

      cy.get(`[data-cy="category"]`).type('15656');
      cy.get(`[data-cy="category"]`).should('have.value', '15656');

      cy.get(`[data-cy="targetQuantity"]`).type('3727');
      cy.get(`[data-cy="targetQuantity"]`).should('have.value', '3727');

      cy.get(`[data-cy="unit"]`).type('generally');
      cy.get(`[data-cy="unit"]`).should('have.value', 'generally');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        targetInventory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', targetInventoryPageUrlPattern);
    });
  });
});
