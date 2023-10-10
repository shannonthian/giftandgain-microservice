import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { WishListState, getHighPriorityWishList, getLowPriorityWishList } from 'app/shared/reducers/wish-list';

export const DonateRight = () => {
  const dispatch = useAppDispatch();

  const { highPriorityWishList, lowPriorityWishList }: WishListState = useAppSelector(state => state.wishList);

  /* uncomment section for cloud connection testing
  useEffect(() => {
    fetch('http://inventory-lb-internal-0a373638bf1c298a.elb.us-east-1.amazonaws.com/giftandgain/inventory', {
      credentials: 'include',
    })
      .then(response => response.json())
      .then(data => console.log(data));
  }, []);
  */

  useEffect(() => {
    const today = new Date();
    const month = today.getMonth() + 1;
    const year = today.getFullYear();
    dispatch(
      getHighPriorityWishList({
        month,
        year,
      })
    );
    dispatch(
      getLowPriorityWishList({
        month,
        year,
      })
    );
  }, []);

  const convertListToListing = (list: string[]) => {
    return list.map((item, index) => {
      return <b key={index}>{index + 1}. {item}<br /></b>
    });
  }
  const highPriorityListing = convertListToListing(highPriorityWishList);
  const lowPriorityListing = convertListToListing(lowPriorityWishList);

  return (
    <div className="p-5">
      <h2>
        Donate Right
      </h2>
      <br />
      <h3>
        What is donating right?
      </h3>
      <p className="lead " style={{ textAlign: "justify" }}>
        <b>
          The act of donating is one of goodwill and with the intention to help others.
          However, not all food items are suitable for donation, such as expired products and junk food.
          The best donations are those that are well-portioned, safe and easy to consume,
          and are nutrionally-appropriate for our beneficiaries.
          <br /> <br />
          <u>Safe and Easy to Consume</u>
          <br />
          As a rule of thumb, we only accept food items that have <b>expiry dates of 3 months and beyond</b>.
          We need to account for the time taken for donation items to reach our beneficiaries,
          and the time they take to finish the food.
          Food items that have opened and damaged packaging are also unacceptable.
          Such unsafe food items might become the catalyst for other issues,
          which could create more harm than good for our beneficiaries.
          <br /><br />
          If you are unsure about the suitability of your item for donation, do check it out <Link to="/donate-right/check-my-item">here</Link>.
        </b>
      </p>
      {/*
      <Link to="/donate-right/check-my-item" className="btn btn-primary">
        <FontAwesomeIcon icon="search" />
        &nbsp;
        <Translate contentKey="global.menu.donateRight.checkMyItem">Check My Item</Translate>
      </Link>
      <br />
      */}
      <br />
      <h3>
        What items do our beneficiaries need?
      </h3>
      <p className="lead " style={{ textAlign: "justify" }}>
        <b>
          We actively update our wish list below according to donations we have already received.
          <br />
          We would greatly appreciate your help to fulfil our beneficiaries wish list below.
          <br /> <br />
          <u id="wish-list"><b>Food Donation Wish List</b></u>
          <br />
          {highPriorityWishList.length ? highPriorityListing : lowPriorityListing}
        </b>
      </p>
      {/* 
      <br />
      <h5>
        <u>High Priority Wish List</u><br />
        1. <br />
        2. <br />
        3. <br />
      </h5>
      <br />
      <h5 style={{ fontWeight: "normal" }}>
        <u>Low Priority Wish List</u><br />
        1. <br />
        2. <br />
        3. <br />
      </h5>
      */}
    </div>
  );
};

export default DonateRight;
