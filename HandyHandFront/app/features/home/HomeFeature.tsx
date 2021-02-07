import React, { useState, useEffect } from 'react';
import { Col, Container, Form, Row } from 'react-bootstrap';
import ContentPage from '../../containers/ContentPage';

export default function HomeFeature() {
  const [seconds, setSeconds] = useState(0);

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
      console.log(seconds);
      setSeconds((seconds) => seconds + 1);
    }, 500);
    return () => clearInterval(interval);
  }, [seconds]);

  return (
    <ContentPage childrenName={'Home'}>
      <Container fluid >
        <Row>
          <Col sm={9} />
          <Col sm={3}>
            <Form>
              <Form.Check type="switch" id="custom-switch" />
            </Form>
          </Col>
        </Row>
        <Row>
          <Col />
          <Col >{getCurrentImage()}</Col>
          <Col />
        </Row>
      </Container>
    </ContentPage>
  );
}
