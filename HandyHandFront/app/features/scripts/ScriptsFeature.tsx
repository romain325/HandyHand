import React, { useEffect, useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';

import ContentPage from '../../containers/ContentPage';
import { allCards, allList } from '../../utils/display/scriptDisplay';
import { ScriptCard } from '../../utils/HandyHandAPI/HandyHandAPIType';

export default function ScriptsFeatures() {
  const [isGrid, setIsGrid] = useState(true);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<ScriptCard[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/scriptDB/all')
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
      <Container fluid>
        <img
          src={
            isGrid ? '../resources/img/grid.png' : '../resources/img/list.png'
          }
          height="25px"
          width="25px"
          style={{
            margin: '10px',
          }}
          onClick={(_e) => {
            setIsGrid(!isGrid);
          }}
        />
      </Container>

      <Container
        fluid
        style={{
          overflow: 'scroll',
          overflowX: 'hidden',
          height: '70vh',
        }}
      >
        { items.length == 0 ? (
          <Col>Nothing Found ...</Col>
        ) : isGrid ? (
          allCards(items)
        ) : (
          allList(items)
        ) }
      </Container>
    </ContentPage>
  );
}
