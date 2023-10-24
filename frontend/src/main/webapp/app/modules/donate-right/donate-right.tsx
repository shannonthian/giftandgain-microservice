import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { WishListState, getHighPriorityWishList, getLowPriorityWishList } from 'app/shared/reducers/wish-list';

export const DonateRight = () => {
  const dispatch = useAppDispatch();

  const { highPriorityWishList, lowPriorityWishList }: WishListState = useAppSelector(state => state.wishList);
  const data = {
    inventoryId: "101",
    itemName: "rice",
    categoryId: "101",
    receivedQuantity: 20,
    expiryDate: "2025-10-10",
    createdDate: "2025-10-10",
    remarks: "hello",
    createdBy: "admin"
  };

  // Define the URL of the backend endpoint
  const url = 'https://qh7hxkd331.execute-api.us-east-1.amazonaws.com/inventory/giftandgain/inventory/create';

  // Define the headers for the POST request
  const headers = {
    'Content-Type': 'application/json',
    // You may need to include additional headers like authorization tokens, if required by your backend
  };

//uncomment section for cloud connection testing
//   useEffect(() => {
//     fetch('https://qh7hxkd331.execute-api.us-east-1.amazonaws.com/inventory/giftandgain/inventory', {
//       credentials: 'include',
//     })
//       .then(response => response.json())
//       .then(data => console.log(data));
//     // Define the data to send

// // Create the POST request
// fetch(url, {
//   method: 'POST',
//   headers: headers,
//   body: JSON.stringify(data)
// })
//   .then(response => {
//     if (!response.ok) {
//       throw new Error(`HTTP error! Status: ${response.status}`);
//     }
//     return response.json();
//   })
//   .then(responseData => {
//     // Handle the response from the server here
//     console.log('Response from server:', responseData);
//   })
//   .catch(error => {
//     // Handle any errors that occurred during the fetch
//     console.error('Error:', error);
//   });
//   }, []);

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
      return <b key={index}>&#x2022; {item}<br /></b>
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
      <p className="lead" style={{ textAlign: "justify" }}>
        <b>
          The act of donating is one of goodwill and with the intention to help others.
          However, not all food items are suitable for donation, such as expired products and junk food.
          The best donations are those that are well-portioned, safe and easy to consume,
          and are nutrionally-appropriate for our beneficiaries.
          <br /> <br />
          <u><b>Safe and Easy to Consume</b></u>
          <br />
          As a rule of thumb, we only accept food items that have
          <b style={{ color: "red" }}> expiry dates of 3 months and beyond</b>.
          We need to account for the time taken for donation items to reach our beneficiaries,
          and the time they take to finish the food.
          Food items that have opened and damaged packaging are also unacceptable.
          Such unsafe food items might become the catalyst for other issues,
          which could create more harm than good for our beneficiaries.
          <br /><br />
          If you are unsure about the suitability of your item for donation, do check it out <Link to="/donate-right/check-my-item">here</Link>.
        </b>
      </p>
      <br />
      <h3>
        What items do our beneficiaries need?
      </h3>
      <p className="lead" style={{ textAlign: "justify" }}>
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
    </div>
  );
};

export default DonateRight;
