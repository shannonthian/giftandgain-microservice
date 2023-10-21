import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { AxiosResponse } from 'axios';
import { toast } from 'react-toastify';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import dayjs from 'dayjs';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { APP_LOCAL_DATE_FORMAT, DATE_FORMAT } from 'app/config/constants';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurrentInventory } from 'app/shared/model/current-inventory.model';
import { getEntity, updateEntity, createEntity, reset } from './current-inventory.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategoryEntities } from '../category/category.reducer';
import { ASC } from 'app/shared/util/pagination.constants';
import { AuthenticationState } from 'app/shared/reducers/authentication';

export const CurrentInventoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const currentInventoryEntity: ICurrentInventory = useAppSelector(state => state.currentInventory.entity);
  const loading: boolean = useAppSelector(state => state.currentInventory.loading);
  const updating: boolean = useAppSelector(state => state.currentInventory.updating);
  const updateSuccess: boolean = useAppSelector(state => state.currentInventory.updateSuccess);
  const categoryList: ICategory[] = useAppSelector(state => state.category.entities);
  const { account }: AuthenticationState = useAppSelector(state => state.authentication);

  const handleClose = () => {
    navigate('/current-inventory' + location.search);
  };

  useEffect(() => {
    dispatch(
      getCategoryEntities({
        sort: `category,${ASC}`,
      })
    );
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id))
        .then((response) => {
          const payload = response.payload as AxiosResponse;
          setUnit(payload.data.category.unit);
        });
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      toast.success(translate("giftandgainFrontendApp.currentInventory." + (isNew ? "created" : "updated"), { id: currentInventoryEntity.inventoryId }));
      handleClose();
    }
  }, [updateSuccess]);

  const today = new Date();

  const [unit, setUnit] = useState("");

  const categoryOptions = categoryList.map((item) => {
    if (item.status === 'A')
      return <option key={item.categoryId} value={item.categoryId}>{item.category}</option>
  });

  const onChangeCategory: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    const categoryId = e.target.value;
    if (categoryId) {
      setUnit(categoryList.find((item) => item.categoryId === +categoryId)?.unit);
    } else {
      setUnit("");
    }
  };

  const saveEntity = values => {
    console.log(values);
    const entity: ICurrentInventory = {
      ...currentInventoryEntity,
      ...values,
      category: {
        categoryId: values.category,
      },
      createdBy: isNew ? account.username : currentInventoryEntity.createdBy,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="giftandgainFrontendApp.currentInventory.home.createOrEditLabel" data-cy="CurrentInventoryCreateUpdateHeading">
            <Translate contentKey={isNew ? "giftandgainFrontendApp.currentInventory.home.createLabel" : "giftandgainFrontendApp.currentInventory.home.editLabel"}>
              Create or edit a CurrentInventory
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  label={translate('global.field.id')}
                  id="current-inventory-id"
                  name="inventoryId"
                  data-cy="inventoryId"
                  type='text'
                  readOnly
                  defaultValue={currentInventoryEntity.inventoryId}
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    validate: v => isNumber(v) || translate('entity.validation.number'),
                  }}
                />
              ) : null}
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.itemName')}
                id="current-inventory-itemName"
                name="itemName"
                data-cy="itemName"
                type="text"
                defaultValue={!isNew ? currentInventoryEntity.itemName : ""}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.category')}
                id="current-inventory-category"
                name="category"
                data-cy="category"
                type="select"
                defaultValue={!isNew ? currentInventoryEntity.category?.categoryId : ""}
                onChange={onChangeCategory}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              >
                <option></option>
                {categoryOptions}
              </ValidatedField>
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.receivedQuantity')}
                id="current-inventory-receivedQuantity"
                name="receivedQuantity"
                data-cy="receivedQuantity"
                type="text"
                defaultValue={!isNew ? currentInventoryEntity.receivedQuantity : ""}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              {unit ? (
                <ValidatedField
                  label={translate('giftandgainFrontendApp.category.unit')}
                  id="current-inventory-unit"
                  name="unit"
                  data-cy="unit"
                  type="text"
                  disabled
                  value={unit}
                />
              ) : null}
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.expiryDate')}
                id="current-inventory-expiryDate"
                name="expiryDate"
                data-cy="expiryDate"
                type="date"
                defaultValue={!isNew ? currentInventoryEntity.expiryDate : ""}
                min={dayjs(today).format(DATE_FORMAT)}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: dayjs(today).format(DATE_FORMAT), message: translate('entity.validation.minDate', { minDate: dayjs(today).format(APP_LOCAL_DATE_FORMAT) }) }
                }}
              />
              {!isNew ? (
                <ValidatedField
                  label={translate('giftandgainFrontendApp.currentInventory.createdBy')}
                  id="current-inventory-createdBy"
                  name="createdBy"
                  data-cy="createdBy"
                  type="text"
                  readOnly
                  defaultValue={currentInventoryEntity.createdBy}
                />
              ) : null}
              {!isNew ? (
                <ValidatedField
                  label={translate('giftandgainFrontendApp.currentInventory.createdDate')}
                  id="current-inventory-createdDate"
                  name="createdDate"
                  data-cy="createdDate"
                  type="date"
                  readOnly
                  defaultValue={currentInventoryEntity.createdDate}
                />
              ) : null}
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.remarks')}
                id="current-inventory-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
                defaultValue={!isNew ? currentInventoryEntity.remarks : ""}
                validate={{
                  maxLength: { value: 255, message: translate('entity.validation.maxlength', { max: 255 }) },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/current-inventory" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CurrentInventoryUpdate;
