import React, { useEffect, useState } from 'react';
import { Col, Row, Container } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import { ScriptCard } from '../../utils/HandyHandAPI/HandyHandAPIType';
import { allCards, allList } from '../../utils/display/scriptDisplay';

export default function MyScriptsFeature() {
  const [isGrid, setIsGrid] = useState(true);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<ScriptCard[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/script/all')
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
        <Link to={routes.ADD_SCRIPT}>
          <img
            src="../resources/img/ajouterIcon.png"
            height="25px"
            width="25px"
            style={{ margin: '15px' }}
            alt="Add"
          />
        </Link>
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
        )}
      </Container>
    </ContentPage>
  );
}
