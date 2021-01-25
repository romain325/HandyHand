import React, { useState } from 'react';
import {  Col, Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import CardScript from '../../components/CardScript';
import ContentPage from '../../containers/ContentPage';
import styles from './ScriptsFeature.css';
import LineScript from '../../components/LineScript';
import src from 'electron-log';

export default function ScriptsFeatures() {
  const [isGrid, setIsGrid] = useState(true);
  const nbElement: number = 15;

  function allCards(): JSX.Element {
    var elements: JSX.Element[] = [];

    let i: number = nbElement;
    while (i > 0) {
      var subElements: JSX.Element[] = [];
      var iter: number = i < 3 ? i : 3;

      for (let j = 0; j < iter; j++) {
        subElements.push(
          <Col>
            <CardScript title="test" description="test" />
          </Col>
        );
      }
      if (iter == 2) {
        subElements.push(<Col></Col>);
      }

      elements.push(<Row>{subElements}</Row>);
      i -= 3;
    }

    return <div>{elements}</div>;
  }

  function allList(): JSX.Element {
    var elements: JSX.Element[] = [];
    for (let i = 0; i < nbElement; i++) {
      elements.push(
        <Row>
          <LineScript />
        </Row>
      );
    }

    return <div>{elements}</div>;
  }
 /*
      <Form className={styles.row}>
        <Form.Check
          type="switch"
          id="isgridswitch"
          label="Mode grille"
          checked={isGrid}
          onClick={(e) => {
            console.log(e);
            setIsGrid(!isGrid);
          }}
        />
      </Form>
      */
  return (
    <ContentPage>
      <Container fluid>
        <img src="C:\Users\thoma\Documents\Projets\HandyHand\HandyHandFront\resources\img\grid.png"
        height="30px"
        width="30px"
        onClick={(e) => {
            console.log(e);
            setIsGrid(!isGrid);
          }}/>
      </Container>

      <Container fluid
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
