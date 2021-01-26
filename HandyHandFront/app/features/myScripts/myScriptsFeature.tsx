import React, { useEffect, useState } from 'react';
import { Col, Row, Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import CardScript from '../../components/CardScript';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import styles from './myScriptsFeature.css';

interface ItemAPI {
  description: string;
  file: string;
  id: string;
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
      <ContentPage>
        <Col>Loading...</Col>
      </ContentPage>
    );
  }

  return (
    <ContentPage>
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
            <Button className={styles.button1} variant="success">
              Ajouter un script
            </Button>
          </Link>
        </Row>
        <Row>TEST</Row>
        <Row>
          {items.length == 0 ? (
            <Col>Nothing Found ...</Col>
          ) : (
            items.map((item) => (
              <Col>
                <CardScript title={item.file} description={item.description} />
              </Col>
            ))
          )}
        </Row>
      </Container>
    </ContentPage>
  );
}
