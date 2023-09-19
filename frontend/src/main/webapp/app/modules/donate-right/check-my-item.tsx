import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Alert, Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export const CheckMyItem = () => {
  const [showResult, setShowResult] = useState(false);

  const checkItem = () => {
    setShowResult(true);
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="global.menu.donateRight.checkMyItem">
            <Translate contentKey="global.menu.donateRight.checkMyItem">
              Check My Item
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm onSubmit={checkItem}>
            <ValidatedField
              label={translate('giftandgainFrontendApp.currentInventory.itemName')}
              id="itemName"
              name="itemName"
              data-cy="itemName"
              type="text"
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
                maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
              }}
            />
            <ValidatedField
              label={translate('giftandgainFrontendApp.currentInventory.expiryDate')}
              id="expiryDate"
              name="expiryDate"
              data-cy="expiryDate"
              type="date"
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
              }}
            />
            <Button tag={Link} id="cancelBtn" to="/donate-right" replace color="info">
              <FontAwesomeIcon icon="arrow-left" />
              &nbsp;
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
            </Button>
            &nbsp;
            <Button color="primary" id="checkItemBtn" type="submit">
              <FontAwesomeIcon icon="circle-check" />
              &nbsp;
              <Translate contentKey="entity.action.check">Check</Translate>
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
      <br />
      {showResult ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="success">
              This item is perfect for donation.
              Do drop off the item ASAP at our donation boxes.
            </Alert>
          </Col>
        </Row>
      ) : null}
      {showResult ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="warning">
              As we have sufficient stock of similar items, we would prefer an expiry date of 6 months and beyond for such items.
              Do check out the items on our high priority list <Link to="/donate-right">here</Link>.
            </Alert>
          </Col>
        </Row>
      ) : null}
      {showResult ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="danger">
              This item is <u>NOT</u> suitable for donation as the expiry date is less than 3 months away.
              We need to account for the time taken for donation items to reach our beneficiaries, and the time they take to finish them.
            </Alert>
          </Col>
        </Row>
      ) : null}
      {showResult ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="danger">
              This item is <u>NOT</u> suitable for donation as it is nutritionally inappropriate.
              We prefer low-sugar and high-protein items as we need to maintain the health of our beneficiaries too.
            </Alert>
          </Col>
        </Row>
      ) : null}
    </div>
  );
};

export default CheckMyItem;
