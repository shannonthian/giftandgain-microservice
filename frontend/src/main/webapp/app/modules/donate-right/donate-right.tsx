import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const DonateRight = () => {
  useEffect(() => {
    fetch('https://ilvlzzz7b9.execute-api.us-east-1.amazonaws.com/stage1/giftandgain/inventory', {
      credentials: 'include',
    })
      .then(response => response.json())
      .then(data => console.log(data));
  }, []);
  return (
    <div className="p-5">
      <h3>DONATE RIGHT</h3>
      <br />
      <h4>
        What is donating right?
        <br />
        ...
      </h4>
      <br />
      <Link to="/donate-right/check-my-item" className="btn btn-primary">
        <FontAwesomeIcon icon="search" />
        &nbsp;
        <Translate contentKey="global.menu.donateRight.checkMyItem">Check My Item</Translate>
      </Link>
      <br />
      <br />
      <h5>
        <u>High Priority Wish List</u>
        <br />
        1. <br />
        2. <br />
        3. <br />
      </h5>
      <br />
      <h5>
        <u>Low Priority Wish List</u>
        <br />
        1. <br />
        2. <br />
        3. <br />
      </h5>
    </div>
  );
};

export default DonateRight;
