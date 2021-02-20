import React from 'react';
import { Button, Card, Col, Form, Row } from 'react-bootstrap';
import { FaTrash } from 'react-icons/all';
import { propsNameToDisplayName } from '../utils/display/scriptDisplay';
import HandyHandAPI from '../utils/HandyHandAPI/HandyHandAPI';

interface Props {
  title: string;
  description: string;
  id: string;
  isActive?: boolean;
}

export default function CardScript(props: Props): JSX.Element {
  return (
    <Card text="dark" className="mb-2 scriptDisplay" id={props.id}>
      <Card.Header>
        <Row>
          <Col sm={9}>
            <Card.Text>{propsNameToDisplayName(props.title)}</Card.Text>
          </Col>
          <Col sm={1}>
            <Form.Switch
              value={ props.isActive == null ? false : props.isActive }
            />
          </Col>
          <Col sm={1}>
            <Button
              variant="danger"
              onClick={() => {
                new HandyHandAPI().removeScript(props.id);
                window.location.reload(false);
              }}
            >
              <FaTrash />
            </Button>
          </Col>
        </Row>
      </Card.Header>
      <Card.Body>
        <Row>
          <Col sm={10}>
            <Card.Text>{props.description}</Card.Text>
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
}
