import React, { useEffect, useState } from 'react';
import { Col, Row, Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import CardScript from '../../components/CardScript';
import routes from '../../constants/routes.json';
import styles from './Counter.css';
import { ScriptCard } from '../../utils/HandyHandAPI/HandyHandAPIType';
import HandyHandAPI from '../../utils/HandyHandAPI/HandyHandAPI';

export default function Counter() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<ScriptCard[]>([]);

  useEffect(() => {
    new HandyHandAPI().getScriptCards().then((data) => {
      setItems(data);
      setIsLoaded(true);
    });
  }, []);

  if (!isLoaded) {
    return <div>Loading</div>;
  }

  return (
    <div>
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
        <Row />
        <Row>
          {items.length === 0 ? (
            <Col>Nothing Found ...</Col>
          ) : (
            items.map((item) => (
              <Col>
                <CardScript title={item.file} description={item.description} id={item.id} />
              </Col>
            ))
          )}
        </Row>
      </Container>
    </div>
  );
}
