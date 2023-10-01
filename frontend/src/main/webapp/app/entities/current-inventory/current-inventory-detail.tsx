import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurrentInventory } from 'app/shared/model/current-inventory.model';
import { getEntity } from './current-inventory.reducer';

export const CurrentInventoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const currentInventoryEntity: ICurrentInventory = useAppSelector(state => state.currentInventory.entity);
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
          <dd>{currentInventoryEntity.inventoryId}</dd>
          <dt>
            <span id="itemName">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.itemName">Item Name</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.itemName}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.category">Category</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.category?.category}</dd>
          <dt>
            <span id="receivedQuantity">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.receivedQuantity">Received Quantity</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.receivedQuantity}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="giftandgainFrontendApp.category.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.category?.unit}</dd>
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
            <span id="createdBy">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {currentInventoryEntity.createdDate ? (
              <TextFormat value={currentInventoryEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{currentInventoryEntity.remarks}</dd>
        </dl>
        <Button tag={Link} to="/current-inventory" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/current-inventory/${currentInventoryEntity.inventoryId}/edit`} replace color="primary">
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
