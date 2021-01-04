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
      <Container>
        <Row className={styles.row} >
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
