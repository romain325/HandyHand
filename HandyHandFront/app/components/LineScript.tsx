import React from 'react';
import { Card, Form, Row, Col, Button } from 'react-bootstrap';
import { FaTrash } from 'react-icons/all';
import { propsNameToDisplayName } from '../utils/display/scriptDisplay';
import HandyHandAPI from '../utils/HandyHandAPI/HandyHandAPI';

interface Props {
  title: string;
  description: string;
  id: string;
  isActive?: boolean;
}

export default function LineScript(props: Props): JSX.Element {
  return (
    <Card
      text="dark"
      style={{
        margin: '8px',
      }}
    >
      <Card.Header>
        <Row>
          <Col sm={2}>
            <Card.Text>{propsNameToDisplayName(props.title)}</Card.Text>
          </Col>
          <Col sm={8}>
            <p
              style={{
                fontSize: 18,
              }}
            >
              {props.description}
            </p>
          </Col>
          <Col sm={1}>
            <Form.Switch
              value={props.isActive == null ? false : props.isActive}
            />
          </Col>
          <Col sm={1}>
            <Button
              variant='danger'
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
    </Card>
  );
}
