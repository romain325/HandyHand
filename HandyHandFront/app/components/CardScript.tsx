import React from 'react';
import { Card, Form } from 'react-bootstrap';
import { Row, Col } from 'react-bootstrap';
import {propsNameToDisplayName} from "../utils/display/scriptDisplay";

interface Props {
  title: string;
  description: string;
  isActive?: boolean;
}

export default function CardScript(props: Props): JSX.Element {
  return (
    <Card text="dark" className="mb-2">
      <Card.Header>
        <Row>
          <Col sm={8}>
            <Card.Text>{propsNameToDisplayName(props.title)}</Card.Text>
          </Col>
          <Col sm={1}>
            <Form>
              <Form.Switch value={props.isActive == null ? false : props.isActive}/>
            </Form>
          </Col>
        </Row>
      </Card.Header>
      <Card.Body>
        <Row>
          <Col sm={10}>
            <Card.Text>
              {props.description}
            </Card.Text>
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
}
