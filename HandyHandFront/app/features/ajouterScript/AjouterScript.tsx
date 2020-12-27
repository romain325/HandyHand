import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';
import { Card, Col, Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Button, Form } from 'react-bootstrap';
import styles from './AjouterScripts.css';
import ContentPage from '../../containers/ContentPage';

export default function AjouterScriptFeature() {
  const [isOpen, setOpen] = useState<boolean>(false);

  function toggleNavBar() {
    setOpen(!isOpen);
    console.log(isOpen);
  }

  return (
    <ContentPage>
      <Container>
        <Row>
          <Form>
            <Form.Group controlId="formGridAddress1">
              <Form.Label>Nom du script</Form.Label>
              <Form.Control placeholder="..." />
            </Form.Group>

            <Form.Group controlId="formGridAddress1">
              <Form.Label>Description</Form.Label>
              <Form.Control as="textarea" rows={3} />
            </Form.Group>

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
            <Link to={routes.COUNTER}>
              <Button variant="primary">Valider</Button>
            </Link>
          </Form>
        </Row>
      </Container>
    </ContentPage>
  );
}
