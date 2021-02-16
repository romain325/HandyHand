import React from 'react';
import { Card, Form, Row, Col } from 'react-bootstrap';
import { propsNameToDisplayName } from '../utils/display/scriptDisplay';

interface Props {
  title: string;
  description: string;
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
          <Col sm={9}>
            <p
              style={{
                fontSize: 18,
              }}
            >
              {props.description}
            </p>
          </Col>
          <Col sm={1}>
            <Form>
              <Form.Switch
                value={props.isActive == null ? false : props.isActive}
              />
            </Form>
          </Col>
        </Row>
      </Card.Header>
    </Card>
  );
}
