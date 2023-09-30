import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Alert, Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITargetInventory } from 'app/shared/model/target-inventory.model';
import { getEntities } from './target-inventory.reducer';

import { addMonth } from 'app/shared/util/date-utils';
import { DATE_DISPLAY_FORMAT, DATE_FORMAT, getStatusDesc } from 'app/config/constants';

export const TargetInventory = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(location, 'targetId'), location.search));

  const targetInventoryList: ITargetInventory[] = useAppSelector(state => state.targetInventory.entities);
  const loading: boolean = useAppSelector(state => state.targetInventory.loading);

  const currentMonthTargets = targetInventoryList.filter((item) => item.targetMonthYear === addMonth(0, DATE_FORMAT));
  const nextMonthTargets = targetInventoryList.filter((item) => item.targetMonthYear === addMonth(1, DATE_FORMAT));

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="target-inventory-heading" data-cy="TargetInventoryHeading">
        <Translate contentKey="giftandgainFrontendApp.targetInventory.home.title">Target Inventories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="giftandgainFrontendApp.targetInventory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/target-inventory/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="giftandgainFrontendApp.targetInventory.home.createLabel">Create new Target Inventory</Translate>
          </Link>
        </div>
      </h2>
      <br />
      <h4>{addMonth(0, DATE_DISPLAY_FORMAT)}</h4>
      <div className="table-responsive">
        {currentMonthTargets && currentMonthTargets.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('targetId')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetId')} />
                </th>
                {/* 
                <th className="hand" onClick={sort('targetMonthYear')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.targetMonthYear">Target Month Year</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetMonthYear')} />
                </th>
                */}
                <th className="hand" onClick={sort('category')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.category">Category</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('category')} />
                </th>
                <th className="hand" onClick={sort('targetQuantity')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.targetQuantity">Target Quantity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetQuantity')} />
                </th>
                <th className="hand" onClick={sort('unit')}>
                  <Translate contentKey="giftandgainFrontendApp.category.unit">Unit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('unit')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="giftandgainFrontendApp.category.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {currentMonthTargets.map((targetInventory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/target-inventory/${targetInventory.targetId}`} color="link" size="sm">
                      {targetInventory.targetId}
                    </Button>
                  </td>
                  {/* <td>{targetInventory.targetMonthYear}</td> */}
                  <td>{targetInventory.category.category}</td>
                  <td>{targetInventory.targetQuantity}</td>
                  <td>{targetInventory.category.unit}</td>
                  <td>{getStatusDesc(targetInventory.category.status)}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/target-inventory/${targetInventory.targetId}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/target-inventory/${targetInventory.targetId}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/target-inventory/${targetInventory.targetId}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="giftandgainFrontendApp.targetInventory.home.notFound">No Target Inventories found</Translate>
            </div>
          )
        )}
      </div>
      <br />
      <h4>{addMonth(1, DATE_DISPLAY_FORMAT)}</h4>
      <div className="table-responsive">
        {nextMonthTargets && nextMonthTargets.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('targetId')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetId')} />
                </th>
                {/*
                <th className="hand" onClick={sort('targetMonthYear')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.targetMonthYear">Target Month Year</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetMonthYear')} />
                </th>
                */}
                <th className="hand" onClick={sort('category')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.category">Category</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('category')} />
                </th>
                <th className="hand" onClick={sort('targetQuantity')}>
                  <Translate contentKey="giftandgainFrontendApp.targetInventory.targetQuantity">Target Quantity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetQuantity')} />
                </th>
                <th className="hand" onClick={sort('unit')}>
                  <Translate contentKey="giftandgainFrontendApp.category.unit">Unit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('unit')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="giftandgainFrontendApp.category.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nextMonthTargets.map((targetInventory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/target-inventory/${targetInventory.targetId}`} color="link" size="sm">
                      {targetInventory.targetId}
                    </Button>
                  </td>
                  {/* <td>{targetInventory.targetMonthYear}</td> */}
                  <td>{targetInventory.category.category}</td>
                  <td>{targetInventory.targetQuantity}</td>
                  <td>{targetInventory.category.unit}</td>
                  <td>{getStatusDesc(targetInventory.category.status)}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/target-inventory/${targetInventory.targetId}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/target-inventory/${targetInventory.targetId}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/target-inventory/${targetInventory.targetId}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="giftandgainFrontendApp.targetInventory.home.notFound">No Target Inventories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TargetInventory;
