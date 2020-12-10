import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Col, Container, Row, Button } from 'react-bootstrap';

import ButtonGroup from 'react-bootstrap/ButtonGroup';
import SideBar from '../SideBar/SideBar';

import routes from '../../constants/routes.json';
import styles from './Counter.css';
import Navperso from '../../components/Navbar/Navbar';

export default function Counter() {
  const [isOpen, setOpen] = useState<boolean>(false);

  function toggleNavBar() {
    setOpen(!isOpen);
    console.log(isOpen);
  }

  return (
    <Container>
      <Navperso />
      <Row>
        <Col>
          <div className={styles.backButton} data-tid="backButton">
            <Link to={routes.HOME}>
              <i className="fa fa-arrow-left fa-3x" />
            </Link>
          </div>

          <Button onClick={toggleNavBar}>YOUHOU</Button>

          <Container>
            <Row>
              <Col>
                <ButtonGroup vertical />
              </Col>
              <Col xs={6}>2 of 3 (wider)</Col>
              <Col>3 of 3</Col>
            </Row>
          </Container>

          <Container className={styles.Contain}>
            <Row className="border border-dark ">
              <Col className="border border-dark ">Nom</Col>
              <Col className="border border-dark ">Description</Col>
              <Col className="border border-dark ">
                Date de denrière édition
              </Col>
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
        </Col>
      </Row>
      <Row>
        <Form>

          <Form.Row>
            <Col>
            <Form.Group controlId="formGridAddress1">
              <Form.Label>Nom du script</Form.Label>
              <Form.Control placeholder="..." />
            </Form.Group>
            </Col>
            <Col>
            <Form.Group controlId="formGridAddress1">
              <Form.Label>Description</Form.Label>
              <Form.Control placeholder="..." />
            </Form.Group>
            </Col>
          </Form.Row>
          <Form.Group>
            <Form.File
              className="position-relative"
              required
              name="file"
              label="Choisissez un script"
              id="validationFormik107"
              feedbackTooltip
            />
          </Form.Group>
          <Button variant="primary">Valider</Button>
        </Form>
      </Row>
    </Container>
  );
}
