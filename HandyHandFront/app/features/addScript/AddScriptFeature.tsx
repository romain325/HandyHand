import React from 'react';
import { Link } from 'react-router-dom';
import { Button, Form, Row, Container } from 'react-bootstrap';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';

export default function AddScriptFeature() {


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
