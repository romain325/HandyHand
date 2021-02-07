import React, { useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';

import CardScript from '../../components/CardScript';
import ContentPage from '../../containers/ContentPage';
import LineScript from '../../components/LineScript';

export default function ScriptsFeatures() {
  const [isGrid, setIsGrid] = useState(true);
  const nbElement = 15;

  function allCards(): JSX.Element {
    const elements: JSX.Element[] = [];

    let i: number = nbElement;
    while (i > 0) {
      const subElements: JSX.Element[] = [];
      const iter: number = i < 3 ? i : 3;

      for (let j = 0; j < iter; j++) {
        subElements.push(
          <Col>
            <CardScript title="test" description="test" />
          </Col>
        );
      }
      if (iter == 2) {
        subElements.push(<Col />);
      }

      elements.push(<Row>{subElements}</Row>);
      i -= 3;
    }

    return <div>{elements}</div>;
  }

  function allList(): JSX.Element {
    const elements: JSX.Element[] = [];
    for (let i = 0; i < nbElement; i++) {
      elements.push(
        <Row>
          <LineScript />
        </Row>
      );
    }

    return <div>{elements}</div>;
  }

  return (
    <ContentPage childrenName="Scripts">
      <Container fluid>
        <img
          src={
            isGrid ? '../resources/img/grid.png' : '../resources/img/list.png'
          }
          height="30px"
          width="30px"
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
        {isGrid ? allCards() : allList()}
      </Container>
    </ContentPage>
  );
}
