import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Counter.css';
import routes from '../../constants/routes.json';
import { Col, Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import ButtonGroup from 'react-bootstrap/ButtonGroup';


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
          <Col>
            <ButtonGroup vertical>

            </ButtonGroup>
          </Col>
          <Col xs={6}>2 of 3 (wider)</Col>
          <Col>3 of 3</Col>
        </Row>
      </Container>

      <Container className={styles.Contain}>
        <Row className="border border-dark ">
          <Col className="border border-dark ">Nom</Col>
          <Col className="border border-dark ">Description</Col>
          <Col className="border border-dark ">Date de denrière édition</Col>
        </Row>
        <Row className="border border-dark ">
          <Col>Nom</Col>
          <Col>Description</Col>
          <Col>Date de denrière édition</Col>
        </Row>
        <Row className="border border-dark ">
          <Col>Nom</Col>
          <Col>Description</Col>
          <Col>Date de denrière édition</Col>
        </Row>
        <Row className="border border-dark ">
          <Col>Nom</Col>
          <Col>Description</Col>
          <Col>Date de denrière édition</Col>
        </Row>
      </Container>
    </div>
  );
}
