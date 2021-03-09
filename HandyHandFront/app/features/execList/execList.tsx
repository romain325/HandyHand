import React, { useEffect, useState } from 'react';
import { Col, Row, Container } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import { ExecInfo } from '../../utils/HandyHandAPI/HandyHandAPIType';
import ExecLine from '../../components/ExecLine';

export function listDisplay(items: ExecInfo[]): JSX.Element {
  const elements: JSX.Element[] = [];
  for (let i = 0; i < items.length; i++) {
    elements.push(
      <Row>
        <Col>
          <ExecLine id={items[i].id} name={items[i].name} path={items[i].execPath}/>
        </Col>
      </Row>
    );
  }

  return <div>{elements}</div>;
}

export default function ExecFeature() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<ExecInfo[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/exec/all')
      .then((rep) => rep.json())
      .then((json) => {
        setItems(json);
        setIsLoaded(true);
      });
  }, []);

  if (!isLoaded) {
    return (
      <ContentPage childrenName="Loading..">
        <Row>
          <Col xs="10">Loading...</Col>
        </Row>
      </ContentPage>
    );
  }

  return (
    <ContentPage childrenName="Scripts">
      <Container
        fluid
        style={{
          overflow: 'scroll',
          overflowX: 'hidden',
          height: '70vh',
        }}
      >
        {items.length == 0 ? <Col>Nothing Found ...</Col> : listDisplay(items)}
      </Container>
    </ContentPage>
  );
}
