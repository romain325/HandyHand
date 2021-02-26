import React, { useEffect, useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import ContentPage from '../../containers/ContentPage';
import { GestureCard } from '../../utils/HandyHandAPI/HandyHandAPIType';
import { allGestureCards } from '../../utils/display/scriptDisplay';

export default function GestureFeatures() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<GestureCard[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/gesture/all', {
      method: 'GET',
    })
      .then((rep) => rep.json())
      .then((json) => {
        setItems(json);
        console.log(json);
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
        {items.length == 0 ? (
          <Col>Nothing Found ...</Col>
        ) : (
          allGestureCards(items)
        )}
      </Container>
    </ContentPage>
  );
}
