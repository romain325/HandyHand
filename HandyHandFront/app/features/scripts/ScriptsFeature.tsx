import React, { useEffect, useState } from 'react';
import {Col, Container, Modal, Row} from 'react-bootstrap';

import ContentPage from '../../containers/ContentPage';
import { allCards, allList } from '../../utils/display/scriptDisplay';
import {
  GestureCard,
  ScriptCard,
} from '../../utils/HandyHandAPI/HandyHandAPIType';
import { getAuthedHeader } from '../connection/Connexion';
import { getAddress } from '../../utils/HandyHandAPI/HandyHandConfig';


function refreshCards(onLoaded: (scripts: ScriptCard[]) => void) {
  fetch(`${getAddress()}/scriptDB/all`, {
    method: 'GET',
    headers: getAuthedHeader(),
  })
    .then((rep) => rep.json())
    .then((json2) => {
      onLoaded(json2);
    });
}

export default function ScriptsFeatures() {
  const [isGrid, setIsGrid] = useState(true);
  const [isLoaded, setIsLoaded] = useState<boolean>(false);
  const [items, setItems] = useState<ScriptCard[]>([]);
  const [gesture, setGestures] = useState<Map<string, string>>(
    new Map<string, string>()
  );
  const [errorMessage, setErrorMessage] = useState<string>('');

  const refresh = () => {
    refreshCards((scripts) => {
      setItems(scripts);
      setIsLoaded(true);
    });
  };

  useEffect(() => {
    fetch(`${getAddress()}/gestureDB/all`, {
      method: 'GET',
      headers: getAuthedHeader(),
    })
      .then((rep) => rep.json())
      .then((json) => {
        console.log(json);
        const gest = new Map<string, string>();
        for (const r of json as GestureCard[]) {
          gest.set(r.id, r.name);
        }
        gest.set('', 'None');
        console.log(gest);
        setGestures(gest);

        refresh();
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
          allCards(items, gesture, true, refresh, setErrorMessage)
        ) : (
          allList(items, gesture, true, refresh, setErrorMessage)
        )}
      </Container>
      <Modal show={errorMessage !== ''} onHide={() => setErrorMessage('')}>
        <Modal.Header>
          <Modal.Title>{errorMessage}</Modal.Title>
        </Modal.Header>
      </Modal>
    </ContentPage>
  );
}
