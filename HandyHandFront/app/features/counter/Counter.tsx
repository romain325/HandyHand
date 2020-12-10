import React from 'react';
import { Col, Row, Card, Dropdown, Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import CardScript from '../../components/CardScript';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import styles from './Counter.css';
export default function Counter() {
  return (
    <div>
      <div className={styles.backButton} data-tid="backButton">
        <Link to={routes.HOME}>
          <i className="fa fa-arrow-left fa-3x" />
        </Link>
      </div>
      <Container>
        <Row>
          <Button className={styles.button} variant="success">Ajouter un script</Button>
        </Row>
        <Row></Row>
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

      </Container>
    </div>
  );
}
