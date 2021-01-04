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
      <Container>
        <Row>
        <Link to={routes.ADD_SCRIPT}>
          <Button className={styles.button1} variant="success">
            Ajouter un script
          </Button>
          </Link>
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
