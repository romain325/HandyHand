import React, {useState} from 'react';
import { Card, Col, Row } from 'react-bootstrap';
import ContentPage from '../../containers/ContentPage';
import {
  getAddress,
  setAddress,
} from '../../utils/HandyHandAPI/HandyHandConfig';



export default function SettingsFeature() {
  const [addr, setAddr] = useState<string>(getAddress());
  const onAddressChange = (e: React.FormEvent<HTMLInputElement>) => {
    setAddress(e.currentTarget.value);
    setAddr(e.currentTarget.value);
  };

  return (
    <ContentPage childrenName="Settings">
      <Row>
        <Col ></Col>
        <Col md="10">
          <Card
            border="secondary"
            style={{padding: '20px', margin: '30px' }}
          >
            <Card.Title className="text-center">
              <h1>Settings</h1>
            </Card.Title>
            <Card.Body>
              <h3>API REST Address</h3>
              <input type="text" value={addr} onChange={onAddressChange} />
            </Card.Body>
          </Card>
        </Col>
        <Col ></Col>
      </Row>
    </ContentPage>
  );
}
