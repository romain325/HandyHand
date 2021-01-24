import React from 'react';
import { Card, Col, Form, Row } from 'react-bootstrap';
import ContentPage from '../../containers/ContentPage';
import styles from './SettingsFeature.css';

export default function SettingsFeature() {
  return (
    <ContentPage>
      <Row>
        <Col ></Col>
        <Col md="10">
          <Card
            border="secondary"
            style={{padding: '20px', margin: '30px' }}
          >
            <Card.Title className="text-center">
              <h1>Settings</h1>
            </Card.Title>
            <Card.Body>
              <h3>Chemin d'accès a l'éxécuteur</h3>
              <Form.File
                className="position-relative"
                required
                name="file"
                id="validationFormik107"
                feedbackTooltip
              />
            </Card.Body>
          </Card>
        </Col>
        <Col ></Col>
      </Row>
    </ContentPage>
  );
}
