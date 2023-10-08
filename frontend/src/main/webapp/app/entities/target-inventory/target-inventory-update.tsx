import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { AxiosResponse } from 'axios';
import { toast } from 'react-toastify';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { addMonth, convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { DATE_DISPLAY_FORMAT, DATE_FORMAT } from 'app/config/constants';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITargetInventory } from 'app/shared/model/target-inventory.model';
import { getEntity, updateEntity, createEntity, reset } from './target-inventory.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategoryEntities } from '../category/category.reducer';
import { ASC } from 'app/shared/util/pagination.constants';

export const TargetInventoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const targetInventoryEntity: ITargetInventory = useAppSelector(state => state.targetInventory.entity);
  const loading: boolean = useAppSelector(state => state.targetInventory.loading);
  const updating: boolean = useAppSelector(state => state.targetInventory.updating);
  const updateSuccess: boolean = useAppSelector(state => state.targetInventory.updateSuccess);
  const categoryList: ICategory[] = useAppSelector(state => state.category.entities);

  const handleClose = () => {
    navigate('/target-inventory');
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
      toast.success(translate("giftandgainFrontendApp.targetInventory." + (isNew ? "created" : "updated"), { id: targetInventoryEntity.targetId }));
      handleClose();
    }
  }, [updateSuccess]);

  const [unit, setUnit] = useState("");

  const targetMonthOptions = (noOfMonths: number) => {
    const options: JSX.Element[] = [];
    for (let i = 0; i < noOfMonths; i++) {
      options.push(
        <option key={`targetMonthYear-${i}`} value={addMonth(1, i, DATE_FORMAT)}>
          {addMonth(1, i, DATE_DISPLAY_FORMAT)}
        </option>
      );
    }
    return options;
  };

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
    const entity: ITargetInventory = {
      ...targetInventoryEntity,
      ...values,
      category: {
        categoryId: values.category,
      },
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
          <h2 id="giftandgainFrontendApp.targetInventory.home.createOrEditLabel" data-cy="TargetInventoryCreateUpdateHeading">
            <Translate contentKey={isNew ? "giftandgainFrontendApp.targetInventory.home.createLabel" : "giftandgainFrontendApp.targetInventory.home.editLabel"}>
              Create or edit a TargetInventory
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
                  id="target-inventory-id"
                  name="targetId"
                  data-cy="targetId"
                  type='text'
                  readOnly
                  defaultValue={targetInventoryEntity.targetId}
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    validate: v => isNumber(v) || translate('entity.validation.number'),
                  }}
                />
              ) : null}
              <ValidatedField
                label={translate('giftandgainFrontendApp.targetInventory.targetMonthYear')}
                id="target-inventory-targetMonthYear"
                name="targetMonthYear"
                data-cy="targetMonthYear"
                type="select"
                defaultValue={!isNew ? targetInventoryEntity.targetMonthYear : ""}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              >
                {targetMonthOptions(2)}
              </ValidatedField>
              <ValidatedField
                label={translate('giftandgainFrontendApp.targetInventory.category')}
                id="target-inventory-category"
                name="category"
                data-cy="category"
                type="select"
                defaultValue={!isNew ? targetInventoryEntity.category?.categoryId : ""}
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
                label={translate('giftandgainFrontendApp.targetInventory.targetQuantity')}
                id="target-inventory-targetQuantity"
                name="targetQuantity"
                data-cy="targetQuantity"
                type="text"
                defaultValue={!isNew ? targetInventoryEntity.targetQuantity : ""}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              {unit ? (
                <ValidatedField
                  label={translate('giftandgainFrontendApp.category.unit')}
                  id="target-inventory-unit"
                  name="unit"
                  data-cy="unit"
                  type="text"
                  disabled
                  value={unit}
                />
              ) : null}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/target-inventory" replace color="info">
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

export default TargetInventoryUpdate;
