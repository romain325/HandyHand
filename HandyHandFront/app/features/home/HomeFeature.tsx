import React, { useState, useEffect } from 'react';
import { Col, Container, Form, Row } from 'react-bootstrap';
import ContentPage from '../../containers/ContentPage';
import HandyHandAPI from '../../utils/HandyHandAPI/HandyHandAPI';

export default function HomeFeature() {
  const [seconds, setSeconds] = useState(0);
  const [isConnected, setIsConnected] = useState(false);

  function getCurrentImage(): JSX.Element {
    return (
      <img
        src={`http://localhost:8080/leap/view?rdm=${seconds}`}
        alt={`Img ${seconds}`}
        height={700}
        width={700}
      />
    );
  }

  useEffect(() => {
    const interval = setInterval(() => {
      setSeconds((seconds) => seconds + 1);
      new HandyHandAPI().isConnected().then((value) => setIsConnected(value));
    }, 500);
    return () => clearInterval(interval);
  }, [seconds]);

  return (
    <ContentPage childrenName="Home">
      <Container fluid>
        <Row>
          <Col sm={9} />
          <Col sm={3}>
            <Form>
              <Form.Switch
                type="switch"
                id="leap_state"
                checked={isConnected}
              />
              <Form.Label>
                {isConnected ? 'Connected!' : 'Disconnected'}
              </Form.Label>
            </Form>
          </Col>
        </Row>
        <Row>
          <Col />
          <Col>{getCurrentImage()}</Col>
          <Col />
        </Row>
      </Container>
    </ContentPage>
  );
}
