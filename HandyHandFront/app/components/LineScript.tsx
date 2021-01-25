import React from 'react';
import { Card, Form } from 'react-bootstrap';
import { Row, Col } from 'react-bootstrap';


export default function LineScript(): JSX.Element {
  return (
    <Card text="dark" style={{
      width: "150vh",
      margin: "8px"
    }}>
      <Card.Header>
        <Row>
          <Col sm={2}>
            <Card.Text>Spotify</Card.Text>
          </Col>
          <Col sm={9}>
            <p style={{
              fontSize: 18
            }}>
              Montrer deux doigts au leap motion pour lancer spotify
            </p>
          </Col>
          <Col sm={1}>
            <Form >
              <Form.Switch/> {/*//TODO ADD AN ID BASED ON PARAM, as to be uniq */}
            </Form>
          </Col>
        </Row>
      </Card.Header>
    </Card>
  );
}
