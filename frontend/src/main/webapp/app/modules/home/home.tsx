import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { AuthenticationState } from 'app/shared/reducers/authentication';

export const Home = () => {
  const { account }: AuthenticationState = useAppSelector(state => state.authentication);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9" style={{ padding: '20px 80px 20px 50px' }}>
        <h2>
          <Translate contentKey="home.title">Gift & Gain</Translate>
        </h2>
        <p className="lead" style={{ textAlign: 'justify' }}>
          <b>
            Gift & Gain Application aims to be a leading user-friendly digital platform for food charity organizations and potential donors.
            Our platform will streamline the process of managing inventory of donation items received by food charity organizations, and
            keep potential donors informed about items that are in high demand. Testing our CICD!!!!!!!
          </b>
        </p>
        {/*
        <p className="lead">
          <Translate contentKey="home.subtitle">This is your homepage</Translate>
        </p>
        */}
        {account?.username ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.username }}>
                You are logged in as user {account.username}.
              </Translate>
            </Alert>
          </div>
        ) : null}
        {/* (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        ) */}
        {/*
        <p>
          <Translate contentKey="home.question">If you have any question on JHipster:</Translate>
        </p>

        <ul>
          <li>
            <a href="https://www.jhipster.tech/" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.homepage">JHipster homepage</Translate>
            </a>
          </li>
          <li>
            <a href="https://stackoverflow.com/tags/jhipster/info" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.stackoverflow">JHipster on Stack Overflow</Translate>
            </a>
          </li>
          <li>
            <a href="https://github.com/jhipster/generator-jhipster/issues?state=open" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.bugtracker">JHipster bug tracker</Translate>
            </a>
          </li>
          <li>
            <a href="https://gitter.im/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.chat">JHipster public chat room</Translate>
            </a>
          </li>
          <li>
            <a href="https://twitter.com/jhipster" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.follow">follow @jhipster on Twitter</Translate>
            </a>
          </li>
        </ul>

        <p>
          <Translate contentKey="home.like">If you like JHipster, do not forget to give us a star on</Translate>{' '}
          <a href="https://github.com/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">
            GitHub
          </a>
          !
        </p>
        */}
      </Col>
    </Row>
  );
};

export default Home;
