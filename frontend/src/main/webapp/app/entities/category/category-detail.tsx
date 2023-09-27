import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategory } from 'app/shared/model/category.model';
import { getEntity } from './category.reducer';
import { getStatusDesc } from 'app/config/constants';

export const CategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const categoryEntity: ICategory = useAppSelector(state => state.category.entity);

  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoryDetailsHeading">
          <Translate contentKey="giftandgainFrontendApp.category.detail.title">Category</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.categoryId}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="giftandgainFrontendApp.category.category">Category</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.category}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="giftandgainFrontendApp.category.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.unit}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="giftandgainFrontendApp.category.status">Status</Translate>
            </span>
          </dt>
          <dd>{getStatusDesc(categoryEntity.status)}</dd>
        </dl>
        <Button tag={Link} to="/category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/category/${categoryEntity.categoryId}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoryDetail;
