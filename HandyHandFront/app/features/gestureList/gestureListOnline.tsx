import React, { useEffect, useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import ContentPage from '../../containers/ContentPage';
import { GestureCard } from '../../utils/HandyHandAPI/HandyHandAPIType';
import { allGestureCards } from '../../utils/display/scriptDisplay';
import {Link} from "react-router-dom";
import routes from "../../constants/routes.json";
import {getAuthedHeader} from "../connection/Connexion";

export default function GestureDbFeatures() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<GestureCard[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/gestureDB/all', {
      method: 'GET',
      headers: getAuthedHeader(),
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
    <ContentPage childrenName="Gestures">
      <Container fluid>
        <Link to={routes.ADD_GESTURE}>
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
        ) : (
          allGestureCards(items, true)
        )}
      </Container>
    </ContentPage>
  );
}
