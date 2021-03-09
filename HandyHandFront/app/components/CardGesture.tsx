import React from 'react';
import { Button, Card, Col, Row } from 'react-bootstrap';
import { FaTrash } from 'react-icons/all';
import { propsNameToDisplayName } from '../utils/display/scriptDisplay';
import HandyHandAPI from '../utils/HandyHandAPI/HandyHandAPI';

interface Props {
  title: string;
  description: string;
  id: string;
  doubleHand: boolean;
  distanceImportance: boolean;
  isOnline: boolean;
}

export default function CardGesture(props: Props): JSX.Element {
  return (
    <Card text="dark" className="mb-2 scriptDisplay" id={props.id}>
      <Card.Header>
        <Row>
          <Col sm={9}>
            <Card.Text>{propsNameToDisplayName(props.title)}</Card.Text>
          </Col>
          <Col sm={1}>
            <Button
              variant="danger"
              onClick={() => {
                if (props.isOnline) {
                  new HandyHandAPI().removeGesture(props.id);
                } else {
                  new HandyHandAPI()
                    .removeGestureDb(props.id)
                    .then(async (r) => console.log(await r.json()))
                    .catch((err) => console.log(err));
                }
                //window.location.reload(false);
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
          <Col sm={5}>
            <Card.Text>{
              `Distance is ${props.distanceImportance ? '' : 'not '}Important`}</Card.Text>
          </Col>
          <Col sm={5}>
            <Card.Text>
              {props.doubleHand ? 'Double Handed' : 'Single Handed'}
            </Card.Text>
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
}
