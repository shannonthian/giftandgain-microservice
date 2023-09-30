import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITargetInventory } from 'app/shared/model/target-inventory.model';
import { getEntity } from './target-inventory.reducer';

import { convertDateToDateDisplay } from 'app/shared/util/date-utils';
import { getStatusDesc } from 'app/config/constants';

export const TargetInventoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const targetInventoryEntity: ITargetInventory = useAppSelector(state => state.targetInventory.entity);
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
          <dd>{targetInventoryEntity.targetId}</dd>
          <dt>
            <span id="targetMonthYear">
              <Translate contentKey="giftandgainFrontendApp.targetInventory.targetMonthYear">Target Month Year</Translate>
            </span>
          </dt>
          <dd>{convertDateToDateDisplay(targetInventoryEntity.targetMonthYear)}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="giftandgainFrontendApp.targetInventory.category">Category</Translate>
            </span>
          </dt>
          <dd>{targetInventoryEntity.category?.category}</dd>
          <dt>
            <span id="targetQuantity">
              <Translate contentKey="giftandgainFrontendApp.targetInventory.targetQuantity">Target Quantity</Translate>
            </span>
          </dt>
          <dd>{targetInventoryEntity.targetQuantity}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="giftandgainFrontendApp.category.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{targetInventoryEntity.category?.unit}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="giftandgainFrontendApp.category.status">Status</Translate>
            </span>
          </dt>
          <dd>{getStatusDesc(targetInventoryEntity.category?.status)}</dd>
        </dl>
        <Button tag={Link} to="/target-inventory" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/target-inventory/${targetInventoryEntity.targetId}/edit`} replace color="primary">
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
