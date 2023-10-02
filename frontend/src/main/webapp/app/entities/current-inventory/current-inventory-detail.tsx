import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './current-inventory.reducer';

export const CurrentInventoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const currentInventoryEntity = useAppSelector(state => state.currentInventory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="currentInventoryDetailsHeading">
          <Translate contentKey="giftandgainFrontendApp.currentInventory.detail.title">CurrentInventory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.id}</dd>
          <dt>
            <span id="itemName">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.itemName">Item Name</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.itemName}</dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {currentInventoryEntity.expiryDate ? (
              <TextFormat value={currentInventoryEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.quantity}</dd>
        </dl>
        <Button tag={Link} to="/current-inventory" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/current-inventory/${currentInventoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CurrentInventoryDetail;
