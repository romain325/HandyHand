import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';
import { Card, Col, Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Button, Form } from 'react-bootstrap';
import styles from './AjouterScripts.css';

export default function AjouterScriptFeature() {
  const [isOpen, setOpen] = useState<boolean>(false);

  function toggleNavBar() {
    setOpen(!isOpen);
    console.log(isOpen);
  }

  return (
    <Container>
      <Row>
        <Col>
          <div className={styles.backButton} data-tid="backButton">
            <Link to={routes.HOME}>
              <i className="fa fa-arrow-left fa-3x" />
            </Link>
          </div>
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
