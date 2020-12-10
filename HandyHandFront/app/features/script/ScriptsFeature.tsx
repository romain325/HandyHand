import React from 'react';
import { Col, Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import CardScript from '../../components/CardScript';
import ContentPage from '../../containers/ContentPage';
import routes from '../../constants/routes.json';
import styles from './ScriptsFeature.css';

export default function ScriptsFeatures() {
  return (
    <ContentPage>
      <div className={styles.backButton} data-tid="backButton">
        <Link to={routes.HOME}>
          <i className="fa fa-arrow-left fa-3x" />
        </Link>
      </div>
      <Container>
        <Row>
          <Col>
            <CardScript />
          </Col>
          <Col>
            <CardScript />
          </Col>
          <Col>
            <CardScript />
          </Col>
        </Row>
        <Row>
          <Col>
            <CardScript></CardScript>
          </Col>
          <Col>
            <CardScript></CardScript>
          </Col>
          <Col>
            <CardScript></CardScript>
          </Col>
        </Row>
      </Container>
    </ContentPage>
  );
}
