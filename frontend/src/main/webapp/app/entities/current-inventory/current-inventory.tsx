import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Col, Form, FormGroup, Input, Label, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount, ValidatedField, translate, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurrentInventory } from 'app/shared/model/current-inventory.model';
import { getEntities } from './current-inventory.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategoryEntities } from '../category/category.reducer';
import { UserState, getUsers } from 'app/shared/reducers/user';

import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { convertDateToDateStr } from 'app/shared/util/date-utils';

export const CurrentInventory = ({ isManager }) => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'inventoryId'), location.search)
  );

  const currentInventoryList: ICurrentInventory[] = useAppSelector(state => state.currentInventory.entities);
  const loading: boolean = useAppSelector(state => state.currentInventory.loading);
  const totalItems: number = useAppSelector(state => state.currentInventory.totalItems);
  const categoryList: ICategory[] = useAppSelector(state => state.category.entities);
  const { users }: UserState = useAppSelector(state => state.user);

  const [itemName, setItemName] = useState("");
  const [categoryId, setCategoryId] = useState(0);
  const [createdBy, setCreatedBy] = useState("");

  const [expiryDateRange, setExpiryDateRange] = useState([null, null]);
  const [expiryStartDate, expiryEndDate] = expiryDateRange;

  const [createdDateRange, setCreatedDateRange] = useState([null, null]);
  const [createdStartDate, createdEndDate] = createdDateRange;

  const categoryOptions = categoryList.map((item) => {
    if (item.status === 'A')
      return <option key={item.categoryId} value={item.categoryId}>{item.category}</option>
  });

  const userOptions = users.map((item) => {
    return <option key={item.id}>{item.username}</option>
  });

  useEffect(() => {
    dispatch(
      getCategoryEntities({
        sort: `category,${ASC}`,
      })
    );
    dispatch(getUsers());
  }, []);

  const getAllEntities = () => {
    console.log(itemName, categoryId, expiryStartDate, expiryEndDate, createdBy, createdStartDate, createdEndDate);
    dispatch(
      getEntities({
        itemName,
        categoryId,
        expiryStartDateStr: convertDateToDateStr(expiryStartDate),
        expiryEndDateStr: convertDateToDateStr(expiryEndDate),
        createdBy,
        createdStartDateStr: convertDateToDateStr(createdStartDate),
        createdEndDateStr: convertDateToDateStr(createdEndDate),
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?` +
      `${itemName ? `itemName=${itemName}&` : ''}` +
      `${categoryId ? `categoryId=${categoryId}&` : ''}` +
      `${expiryStartDate ? `expiryStartDateStr=${convertDateToDateStr(expiryStartDate)}&` : ''}` +
      `${expiryEndDate ? `expiryEndDateStr=${convertDateToDateStr(expiryEndDate)}&` : ''}` +
      `${createdBy ? `createdBy=${createdBy}&` : ''}` +
      `${createdStartDate ? `createdStartDateStr=${convertDateToDateStr(createdStartDate)}&` : ''}` +
      `${createdEndDate ? `createdEndDateStr=${convertDateToDateStr(createdEndDate)}&` : ''}` +
      `page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    sortEntities();
  };

  const clearSearch = () => {
    setItemName("");
    setCategoryId(0);
    setExpiryDateRange([null, null]);
    setCreatedBy("");
    setCreatedDateRange([null, null]);
  }

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="current-inventory-heading" data-cy="CurrentInventoryHeading">
        <Translate contentKey="giftandgainFrontendApp.currentInventory.home.title">Current Inventories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="giftandgainFrontendApp.currentInventory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/current-inventory/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="giftandgainFrontendApp.currentInventory.home.createLabel">Create new Current Inventory</Translate>
          </Link>
        </div>
      </h2>
      <br />
      <div className='jh-card card'>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col sm="6">
              <FormGroup>
                <Label for="itemName">
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.itemName">
                    Item Name
                  </Translate>
                </Label>
                <Input
                  id="itemName"
                  type="text"
                  value={itemName}
                  onChange={(e) => {
                    setItemName(e.target.value);
                  }}
                />
              </FormGroup>
            </Col>
            <Col sm="6">
              <FormGroup>
                <Label for="category">
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.category">
                    Category
                  </Translate>
                </Label>
                <Input
                  id="category"
                  type="select"
                  value={categoryId}
                  onChange={(e) => {
                    setCategoryId(+e.target.value);
                  }}
                >
                  <option></option>
                  {categoryOptions}
                </Input>
              </FormGroup>
            </Col>
            <Col sm="6">
              <FormGroup>
                <Label for="expiryDate">
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.expiryDate">
                    Expiry Date
                  </Translate>
                </Label>
                <DatePicker
                  id="expiryDate"
                  className="form-control"
                  selectsRange={true}
                  startDate={expiryStartDate}
                  endDate={expiryEndDate}
                  onChange={(update) => {
                    setExpiryDateRange(update);
                  }}
                  dateFormat="dd/MM/yyyy"
                />
              </FormGroup>
            </Col>
            <Col sm="6">
              <FormGroup>
                <Label for="createdBy">
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.createdBy">
                    Created By
                  </Translate>
                </Label>
                <Input
                  id="createdBy"
                  type="select"
                  value={createdBy}
                  onChange={(e) => {
                    setCreatedBy(e.target.value);
                  }}
                >
                  <option></option>
                  {userOptions}
                </Input>
              </FormGroup>
            </Col>
            <Col sm="6">
              <FormGroup>
                <Label for="createdDate">
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.createdDate">
                    Created Date
                  </Translate>
                </Label>
                <DatePicker
                  id="createdDate"
                  className="form-control"
                  selectsRange={true}
                  startDate={createdStartDate}
                  endDate={createdEndDate}
                  onChange={(update) => {
                    setCreatedDateRange(update);
                  }}
                  dateFormat="dd/MM/yyyy"
                />
              </FormGroup>
            </Col>
          </Row>
          <Button color="warning" id="sort-entity" type="submit" disabled={loading}>
            <FontAwesomeIcon icon="search" />
            &nbsp;Search
          </Button>
          &nbsp;
          <Button color="secondary" id="sort-entity" type="button" disabled={loading} onClick={clearSearch}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;Reset
          </Button>
        </Form>
      </div>
      <br />
      <div className="table-responsive">
        {currentInventoryList && currentInventoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('inventoryId')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('inventoryId')} />
                </th>
                <th className="hand" onClick={sort('itemName')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.itemName">Item Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('itemName')} />
                </th>
                <th className="hand" onClick={sort('category')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.category">Category</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('category')} />
                </th>
                <th className="hand" onClick={sort('receivedQuantity')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.receivedQuantity">Received Quantity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('receivedQuantity')} />
                </th>
                <th className="hand">
                  <Translate contentKey="giftandgainFrontendApp.category.unit">Unit</Translate>{' '}
                </th>
                <th className="hand" onClick={sort('expiryDate')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.expiryDate">Expiry Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expiryDate')} />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th className="hand" onClick={sort('remarks')}>
                  <Translate contentKey="giftandgainFrontendApp.currentInventory.remarks">Remarks</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('remarks')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {currentInventoryList.map((currentInventory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/current-inventory/${currentInventory.inventoryId}`} color="link" size="sm">
                      {currentInventory.inventoryId}
                    </Button>
                  </td>
                  <td>{currentInventory.itemName}</td>
                  <td>{currentInventory.category.category}</td>
                  <td>{currentInventory.receivedQuantity}</td>
                  <td>{currentInventory.category.unit}</td>
                  <td>
                    {currentInventory.expiryDate ? (
                      <TextFormat type="date" value={currentInventory.expiryDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{currentInventory.createdBy}</td>
                  <td>
                    {currentInventory.createdDate ? (
                      <TextFormat type="date" value={currentInventory.createdDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{currentInventory.remarks}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/current-inventory/${currentInventory.inventoryId}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {isManager ? (
                        <>
                          &nbsp;
                          <Button
                            tag={Link}
                            to={`/current-inventory/${currentInventory.inventoryId}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            color="primary"
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                        </>
                      ) : null}
                      {isManager ? (
                        <>
                          &nbsp;
                          <Button
                            tag={Link}
                            to={`/current-inventory/${currentInventory.inventoryId}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            color="danger"
                            size="sm"
                            data-cy="entityDeleteButton"
                          >
                            <FontAwesomeIcon icon="trash" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.delete">Delete</Translate>
                            </span>
                          </Button>
                        </>
                      ) : null}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="giftandgainFrontendApp.currentInventory.home.notFound">No Current Inventories found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={currentInventoryList && currentInventoryList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default CurrentInventory;
