import React, { ChangeEvent } from 'react';
import { Card, Row, Col } from 'react-bootstrap';
import { ExecInfo } from '../utils/HandyHandAPI/HandyHandAPIType';
import HandyHandAPI from '../utils/HandyHandAPI/HandyHandAPI';

interface Props {
  name: string;
  path: string;
  id: string;
}

export default function ExecLine(props: Props): JSX.Element {
  const onSubmit = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files?.[0] != null) {
      const returnData: ExecInfo = {
        id: props.id,
        execPath: e.target.files[0].path,
        name: props.name,
      };

      console.log(returnData)
      new HandyHandAPI()
        .modifyExec(returnData)
        .then((r) => {
          window.location.reload();
        })
        .catch((err) => console.log(err));
    }
  };

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
            <Card.Text>{props.name}</Card.Text>
          </Col>
          <Col sm={6}>
            <p
              style={{
                fontSize: 18,
              }}
            >
              {props.path}
            </p>
          </Col>
          <Col sm={2}>
            <input type="file" onChange={onSubmit} />
          </Col>
        </Row>
      </Card.Header>
    </Card>
  );
}
