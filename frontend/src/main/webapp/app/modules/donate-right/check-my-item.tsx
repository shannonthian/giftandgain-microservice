import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { HashLink } from 'react-router-hash-link';
import { Alert, Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { WishListState, getHighPriorityWishList } from 'app/shared/reducers/wish-list';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategoryEntities } from 'app/entities/category/category.reducer';
import { ASC } from 'app/shared/util/pagination.constants';

import { DATE_FORMAT } from 'app/config/constants';
import { addMonth } from 'app/shared/util/date-utils';

export const CheckMyItem = () => {
  const dispatch = useAppDispatch();

  const { highPriorityWishList }: WishListState = useAppSelector(state => state.wishList);
  const categoryList: ICategory[] = useAppSelector(state => state.category.entities);

  useEffect(() => {
    dispatch(
      getCategoryEntities({
        sort: `category,${ASC}`,
      })
    );
    const today = new Date();
    const month = today.getMonth() + 1;
    const year = today.getFullYear();
    dispatch(
      getHighPriorityWishList({
        month,
        year,
      })
    );
  }, []);

  const categoryOptions = categoryList.map((item) => {
    if (item.status === 'A')
      return <option key={item.categoryId} value={item.categoryId}>{item.category}</option>
  });

  const [category, setCategory] = useState("");
  const [result, setResult] = useState("");

  const getCategoryName = (categoryId: number) => {
    return categoryList.find((item) => item.categoryId === categoryId)?.category;
  }

  const checkItem = (values) => {
    const category = getCategoryName(+values.itemCategory);
    const expiryDate = new Date(values.expiryDate);

    const month3 = new Date(addMonth(null, 3, DATE_FORMAT));
    const month6 = new Date(addMonth(null, 6, DATE_FORMAT));

    if (expiryDate >= month6) {
      setResult("P");
    } else if (expiryDate < month3) {
      setResult("F");
    } else if (highPriorityWishList.includes(category)) {
      setResult("P");
    } else {
      setResult("A");
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="global.menu.donateRight.checkMyItem">
            <Translate contentKey="global.menu.donateRight.checkMyItem">
              Check My Item
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">

        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm onSubmit={checkItem}>
            <ValidatedField
              label={"Item " + translate('giftandgainFrontendApp.currentInventory.category')}
              id="itemCategory"
              name="itemCategory"
              data-cy="itemCategory"
              type="select"
              onChange={(e) => setCategory(e.target.value)}
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
              }}
            >
              <option></option>
              {categoryOptions}
            </ValidatedField>
            {!category ? (
              <Alert color="secondary">
                If your item does not belong to any category listed above, we are unfortunately not accepting such items.
                You may check out our beneficiaries wish list <HashLink to="/donate-right#wish-list">here</HashLink>.
              </Alert>
            ) : null}
            <ValidatedField
              label={translate('giftandgainFrontendApp.currentInventory.expiryDate')}
              id="expiryDate"
              name="expiryDate"
              data-cy="expiryDate"
              type="date"
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
              }}
            />
            <Button tag={Link} id="cancelBtn" to="/donate-right" replace color="info">
              <FontAwesomeIcon icon="arrow-left" />
              &nbsp;
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
            </Button>
            &nbsp;
            <Button color="primary" id="checkItemBtn" type="submit">
              <FontAwesomeIcon icon="circle-check" />
              &nbsp;
              <Translate contentKey="entity.action.check">Check</Translate>
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
      <br />
      {result === "P" ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="success">
              This item is perfect for donation.
              Do drop off the item ASAP at our donation boxes.
            </Alert>
          </Col>
        </Row>
      ) : null}
      {result === "A" ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="warning">
              As we have sufficient stock of similar items, we would prefer an expiry date of 6 months and beyond for such items.
              {highPriorityWishList.length ? (
                <>
                  {' '}Do check out the items currently on our beneficiaries wish list <HashLink to="/donate-right#wish-list">here</HashLink>.
                </>
              ) : null}
            </Alert>
          </Col>
        </Row>
      ) : null}
      {result === "F" ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="danger">
              This item is <u>NOT</u> suitable for donation as the expiry date is less than 3 months away.
              We need to account for the time taken for donation items to reach our beneficiaries,
              and the time they take to finish the food.
            </Alert>
          </Col>
        </Row>
      ) : null}
      {/* result === "N" ? (
        <Row className="justify-content-center">
          <Col md="8">
            <Alert color="danger">
              This item is <u>NOT</u> suitable for donation as it is nutritionally inappropriate.
              We prefer low-sugar and high-protein items as we need to maintain the health of our beneficiaries too.
            </Alert>
          </Col>
        </Row>
      ) : null */}
    </div>
  );
};

export default CheckMyItem;
