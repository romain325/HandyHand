import React from 'react';
import { Card } from 'react-bootstrap';
import { Row, Col } from 'react-bootstrap';
import Dropdown from 'react-bootstrap/Dropdown';

export default function CardScript(): JSX.Element {
  return (
    <Card text="dark" style={{ width: '20rem' }} className="mb-2">
      <Card.Header>Spotify </Card.Header>
      <Card.Body>
        <Row>
          <Col sm={10}>
            <Card.Text>
              Monter deux doigt au leap motion pour lancer spotify
            </Card.Text>
          </Col>
          <Col sm={1}>
            <Dropdown key="right" id="dropdown-button-drop-right" drop="right">
              <Dropdown.Toggle variant="secondary" />
              <Dropdown.Menu>
                <Dropdown.Item href="#/action-1">
                  <Card border="secondary">
                    <Card.Header>Désactiver</Card.Header>
                  </Card>
                </Dropdown.Item>
                <Dropdown.Item href="#/action-2">
                  <Card border="secondary">
                    <Card.Header>Activer</Card.Header>
                  </Card>
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
}
