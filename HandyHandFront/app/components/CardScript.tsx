import React from 'react';
import { Card, Form } from 'react-bootstrap';
import { Row, Col } from 'react-bootstrap';


export default function CardScript(): JSX.Element {
  return (
    <Card text="dark" style={{ width: '20rem' }} className="mb-2">
      <Card.Header>
        <Row>
          <Col sm={9}>
            <Card.Text>Spotify</Card.Text>
          </Col>
          <Col sm={1}>
            <Form >
              <Form.Check type="switch" id="custom-switch"/>
            </Form>
          </Col>
        </Row>
      </Card.Header>
      <Card.Body>
        <Row>
          <Col sm={10}>
            <Card.Text>
              Montrer deux doigts au leap motion pour lancer spotify
            </Card.Text>
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
}
