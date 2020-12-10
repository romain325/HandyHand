import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Counter.css';
import routes from '../../constants/routes.json';
import { Card, Col, Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import { Button, Form } from 'react-bootstrap';

export default function Counter() {
  return (
    <Container>
      <Row>
        <Col>
          <div className={styles.backButton} data-tid="backButton">
            <Link to={routes.HOME}>
              <i className="fa fa-arrow-left fa-3x" />
            </Link>
          </div>
        </Col>
      </Row>
      <Row>
        <Form>
          <Form.Row>
            <Form.Group as={Col} md="4" >
          </Form.Row>
          <Form.Group>
            <Form.File
              className="position-relative"
              required
              name="file"
              label="File"
              id="validationFormik107"
              feedbackTooltip
            />
          </Form.Group>
        </Form>
      </Row>
    </Container>
  );
}
