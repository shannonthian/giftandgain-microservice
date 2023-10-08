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

describe('CurrentInventory e2e test', () => {
  const currentInventoryPageUrl = '/current-inventory';
  const currentInventoryPageUrlPattern = new RegExp('/current-inventory(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const currentInventorySample = {
    itemName: 'Transmasculine payment structure',
    category: 31632,
    receivedQuantity: 14545,
    unit: 'Jewelery redundant',
    expiryDate: '2023-09-02',
    createdBy: 5074,
    createdDate: '2023-09-03',
  };

  let currentInventory;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/current-inventories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/current-inventories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/current-inventories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (currentInventory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/current-inventories/${currentInventory.id}`,
      }).then(() => {
        currentInventory = undefined;
      });
    }
  });

  it('CurrentInventories menu should load CurrentInventories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('current-inventory');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CurrentInventory').should('exist');
    cy.url().should('match', currentInventoryPageUrlPattern);
  });

  describe('CurrentInventory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(currentInventoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CurrentInventory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/current-inventory/new$'));
        cy.getEntityCreateUpdateHeading('CurrentInventory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentInventoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/current-inventories',
          body: currentInventorySample,
        }).then(({ body }) => {
          currentInventory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/current-inventories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/current-inventories?page=0&size=20>; rel="last",<http://localhost/api/current-inventories?page=0&size=20>; rel="first"',
              },
              body: [currentInventory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(currentInventoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CurrentInventory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('currentInventory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentInventoryPageUrlPattern);
      });

      it('edit button click should load edit CurrentInventory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CurrentInventory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentInventoryPageUrlPattern);
      });

      it('edit button click should load edit CurrentInventory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CurrentInventory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentInventoryPageUrlPattern);
      });

      it('last delete button click should delete instance of CurrentInventory', () => {
        cy.intercept('GET', '/api/current-inventories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('currentInventory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', currentInventoryPageUrlPattern);

        currentInventory = undefined;
      });
    });
  });

  describe('new CurrentInventory page', () => {
    beforeEach(() => {
      cy.visit(`${currentInventoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CurrentInventory');
    });

    it('should create an instance of CurrentInventory', () => {
      cy.get(`[data-cy="itemName"]`).type('dry Haven');
      cy.get(`[data-cy="itemName"]`).should('have.value', 'dry Haven');

      cy.get(`[data-cy="category"]`).type('7769');
      cy.get(`[data-cy="category"]`).should('have.value', '7769');

      cy.get(`[data-cy="receivedQuantity"]`).type('5102');
      cy.get(`[data-cy="receivedQuantity"]`).should('have.value', '5102');

      cy.get(`[data-cy="unit"]`).type('quicker Usability');
      cy.get(`[data-cy="unit"]`).should('have.value', 'quicker Usability');

      cy.get(`[data-cy="expiryDate"]`).type('2023-09-03');
      cy.get(`[data-cy="expiryDate"]`).blur();
      cy.get(`[data-cy="expiryDate"]`).should('have.value', '2023-09-03');

      cy.get(`[data-cy="createdBy"]`).type('20610');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '20610');

      cy.get(`[data-cy="createdDate"]`).type('2023-09-02');
      cy.get(`[data-cy="createdDate"]`).blur();
      cy.get(`[data-cy="createdDate"]`).should('have.value', '2023-09-02');

      cy.get(`[data-cy="remarks"]`).type('Cheese Visionary');
      cy.get(`[data-cy="remarks"]`).should('have.value', 'Cheese Visionary');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        currentInventory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', currentInventoryPageUrlPattern);
    });
  });
});
