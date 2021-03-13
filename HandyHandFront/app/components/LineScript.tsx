import React, {useState} from 'react';
import {Card, Form, Row, Col, Button, DropdownButton, Dropdown} from 'react-bootstrap';
import { FaTrash } from 'react-icons/all';
import { propsNameToDisplayName } from '../utils/display/scriptDisplay';
import HandyHandAPI from '../utils/HandyHandAPI/HandyHandAPI';

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

export default function LineScript(props: Props): JSX.Element {
  const [isActive, setIsActive] = useState(
    props.isActive == null ? false : props.isActive
  );

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
          <Col sm={6}>
            <p
              style={{
                fontSize: 18,
              }}
            >
              {props.description}
            </p>
          </Col>
          <Col sm={2}>
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
          <Col sm={1}>
            <Form.Check custom id={props.id} type="switch">
              <Form.Check.Input checked={isActive} />
              <Form.Check.Label
                onClick={() => {
                  props.onActiveClic(props.id, isActive);
                  setIsActive(!isActive);
                }}
              />
            </Form.Check>
          </Col>
          <Col sm={1}>
            <Button
              variant='danger'
              onClick={() => props.onDeleteClic(props.id)}
            >
              <FaTrash />
            </Button>
          </Col>
        </Row>
      </Card.Header>
    </Card>
  );
}
