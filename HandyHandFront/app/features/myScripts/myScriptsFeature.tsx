import React, { useEffect, useState } from 'react';
import { Col, Row, Container } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import {
  GestureCard,
  ScriptCard,
} from '../../utils/HandyHandAPI/HandyHandAPIType';
import { allCards, allList } from '../../utils/display/scriptDisplay';
import { getAddress } from '../../utils/HandyHandAPI/HandyHandConfig';

export default function MyScriptsFeature() {
  const [isGrid, setIsGrid] = useState(true);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<ScriptCard[]>([]);
  const [gesture, setGestures] = useState<Map<string, string>>(
    new Map<string, string>()
  );

  useEffect(() => {
    fetch(`${getAddress()}/gesture/all`)
      .then((rep) => rep.json())
      .then((json) => {
        const gest = new Map<string, string>();
        for (const r of json as GestureCard[]) {
          gest.set(r.id, r.name);
        }
        gest.set('', 'None');
        console.log(gest);
        setGestures(gest);

        fetch(`${getAddress()}/script/all`)
          .then((rep) => rep.json())
          .then((json2) => {
            console.log(json2);
            setItems(json2);
            setIsLoaded(true);
          });
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
        {items.length == 0 ? (
          <Col>Nothing Found ...</Col>
        ) : isGrid ? (
          allCards(items, gesture, false)
        ) : (
          allList(items)
        )}
      </Container>
    </ContentPage>
  );
}
