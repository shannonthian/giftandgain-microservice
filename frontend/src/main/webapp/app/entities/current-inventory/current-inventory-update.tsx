import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { toast } from 'react-toastify';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { ASC } from 'app/shared/util/pagination.constants';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurrentInventory } from 'app/shared/model/current-inventory.model';
import { getEntity, updateEntity, createEntity, reset } from './current-inventory.reducer';
import { ITargetInventory } from 'app/shared/model/target-inventory.model';
import { getEntities } from '../target-inventory/target-inventory.reducer';

export const CurrentInventoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const currentInventoryEntity = useAppSelector(state => state.currentInventory.entity);
  const loading = useAppSelector(state => state.currentInventory.loading);
  const updating = useAppSelector(state => state.currentInventory.updating);
  const updateSuccess = useAppSelector(state => state.currentInventory.updateSuccess);
  const targetInventoryList = useAppSelector(state => state.targetInventory.entities);

  const handleClose = () => {
    navigate('/current-inventory' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
    dispatch(getEntities({
      sort: `itemName,${ASC}`
    }));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      toast.success(translate("giftandgainFrontendApp.currentInventory." + (isNew ? "created" : "updated"), { id: currentInventoryEntity.id }));
      handleClose();
    }
  }, [updateSuccess]);

  const itemOptions = targetInventoryList.map((item: ITargetInventory) => {
    return <option key={item.id}>{item.itemName}</option>
  });

  const saveEntity = values => {
    const entity = {
      ...currentInventoryEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
        ...currentInventoryEntity,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  disabled
                  id="current-inventory-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.itemName')}
                id="current-inventory-itemName"
                name="itemName"
                data-cy="itemName"
                type="select"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              >
                <option></option>
                {itemOptions}
              </ValidatedField>
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.expiryDate')}
                id="current-inventory-expiryDate"
                name="expiryDate"
                data-cy="expiryDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('giftandgainFrontendApp.currentInventory.quantity')}
                id="current-inventory-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
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
