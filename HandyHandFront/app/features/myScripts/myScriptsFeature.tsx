import React, { useEffect, useState } from 'react';
import { Col, Row, Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { Item } from 'electron';
import CardScript from '../../components/CardScript';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import styles from './myScriptsFeature.css';

interface ItemAPI {
  description: string;
  file: string;
  id: string;
}

function allCards(items: ItemAPI[]): JSX.Element {
  const elements: JSX.Element[] = [];

  let i: number = items.length;
  while (i > 0) {
    const subElements: JSX.Element[] = [];
    const iter: number = i < 3 ? i : 3;

    for (let j = 0; j < iter; j++) {
      subElements.push(
        <Col>
          <CardScript
            title={items[(i - items.length) * -1 + j].file}
            description={items[(i - items.length) * -1 + j].description}
          />
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

export default function MyScriptsFeature() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<ItemAPI[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/script/all')
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
          <Col>
            <Link to={routes.ADD_SCRIPT}>
              <img
                src="../resources/img/ajouterIcon.png"
                height="25px"
                width="25px"
                style={{ margin: '10px' }}
              />
            </Link>
          </Col>
        </Row>
        <Row>
          <Col xs="10">Loading...</Col>
        </Row>
      </ContentPage>
    );
  }

  return (
    <ContentPage childrenName="My Scripts">
      <Container
        fluid
        style={{
          overflow: 'scroll',
          overflowX: 'hidden',
          height: '70vh',
        }}
      >
        <Row>
          <Link to={routes.ADD_SCRIPT}>
            <img
              src="../resources/img/ajouterIcon.png"
              height="25px"
              width="25px"
              style={{ margin: '15px' }}
              alt="Add"
            />
          </Link>
        </Row>
        <Row>
          {items.length == 0 ? <Col>Nothing Found ...</Col> : allCards(items)}
        </Row>
      </Container>
    </ContentPage>
  );
}
