import React from 'react';
import { Col, Container, Form } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import CardScript from '../../components/CardScript';
import ContentPage from '../../containers/ContentPage';
import routes from '../../constants/routes.json';
import styles from './ScriptsFeature.css';
import ReactDOM from 'react-dom';
import GridScript from '../../components/GridScript';

export default function ScriptsFeatures() {
  /*
  ReactDOM.render(
    <Greeting isLoggedIn={false} />,
    document.getElementById('root')
  );

  function Greeting(props: { isLoggedIn: any }) {
    const isActivated = props.isLoggedIn;
    if (isActivated) {
      return (
        <ContentPage>
          <CardScript />
        </ContentPage>
      );
    }
    return (
      <ContentPage>
        <CardScript />
      </ContentPage>
    );
  */

  return (
    <ContentPage>
      <Form className={styles.row}>
        <Form.Check
          type="switch"
          id="custom-switch"
          label="Mode grille"
        />
      </Form>
      <Container>
        <Row className={styles.row}>
          <Col>
            <CardScript />
          </Col>
          <Col>
            <CardScript />
          </Col>
          <Col>
            <CardScript />
          </Col>
        </Row>
        <Row>
          <Col>
            <CardScript></CardScript>
          </Col>
          <Col>
            <CardScript></CardScript>
          </Col>
          <Col>
            <CardScript></CardScript>
          </Col>
        </Row>
      </Container>
    </ContentPage>
  );
}
