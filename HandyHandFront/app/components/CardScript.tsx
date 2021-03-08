import React, { useState } from 'react';
import {
  Button,
  Card,
  Col,
  Dropdown,
  DropdownButton,
  Form,
  Row,
} from 'react-bootstrap';
import { FaTrash } from 'react-icons/all';
import { propsNameToDisplayName } from '../utils/display/scriptDisplay';

interface Props {
  title: string;
  description: string;
  id: string;
  gestureId: string;
  gestureSet: Map<string, string>;
  onGestureSelect: (gestureId: string, scriptId: string) => void;
  onDeleteClic: (scriptId: string) => void;
  onActiveClic: (scriptId: string, isActive: boolean | undefined) => void;
  isActive?: boolean;
}

export default function CardScript(props: Props): JSX.Element {
  const [isActive, setIsActive] = useState(
    props.isActive == null ? false : props.isActive
  );

  return (
    <Card text="dark" className="mb-2 scriptDisplay" id={props.id}>
      <Card.Header>
        <Row>
          <Col sm={9}>
            <Card.Text>{propsNameToDisplayName(props.title)}</Card.Text>
          </Col>
          <Col sm={1}>
            <Form.Check custom id={props.id} type="switch">
              <Form.Check.Input checked={isActive} />
              <Form.Check.Label
                onClick={() => {
                  props.onActiveClic(props.id, isActive);
                  setIsActive(!isActive);
                }}
              >
                {isActive ? 'Listening' : 'Zzz'}
              </Form.Check.Label>
            </Form.Check>
          </Col>
          <Col sm={1}>
            <Button
              variant="danger"
              onClick={() => {
                props.onDeleteClic(props.id);
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
        <Row>
          <Col>
            <DropdownButton title={props.gestureSet.get(props.gestureId)}>
              {Array.from(props.gestureSet.keys()).map((value, index) => {
                return (
                  <Dropdown.Item
                    key={index.toString()}
                    id={value}
                    onClick={() => props.onGestureSelect(value, props.id)}
                    eventKey={value}
                  >
                    {props.gestureSet.get(value)}
                  </Dropdown.Item>
                );
              })}
            </DropdownButton>
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
}
