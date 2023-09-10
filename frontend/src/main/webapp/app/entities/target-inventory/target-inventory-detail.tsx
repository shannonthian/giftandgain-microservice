import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './target-inventory.reducer';

export const TargetInventoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const targetInventoryEntity = useAppSelector(state => state.targetInventory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="targetInventoryDetailsHeading">
          <Translate contentKey="giftandgainFrontendApp.targetInventory.detail.title">TargetInventory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{targetInventoryEntity.id}</dd>
          <dt>
            <span id="itemName">
              <Translate contentKey="giftandgainFrontendApp.targetInventory.itemName">Item Name</Translate>
            </span>
          </dt>
          <dd>{targetInventoryEntity.itemName}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="giftandgainFrontendApp.targetInventory.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{targetInventoryEntity.quantity}</dd>
        </dl>
        <Button tag={Link} to="/target-inventory" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/target-inventory/${targetInventoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TargetInventoryDetail;
