import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './Counter.css';
import routes from '../../constants/routes.json';
import { Card, Col, Container } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import SideBar from '../SideBar/SideBar';
import { Button } from 'react-bootstrap';
import NavBar from '../../components/NavBar';
import CardScript from '../../components/CardScript';



export default function Counter() {
  const [isOpen, setOpen] = useState<boolean>(false);

  function toggleNavBar() {
    setOpen(!isOpen);
    console.log(isOpen);
  }

  return (
    <Container>
      <div>
        <NavBar />
      </div>
      <Row>
        <Col>
          <SideBar isOpen={isOpen} toggleBar={toggleNavBar} />
        </Col>
      </Row>
      <Row>
        <Col>
          <div className={styles.backButton} data-tid="backButton">
            <Link to={routes.HOME}>
              <i className="fa fa-arrow-left fa-3x" />
            </Link>
          </div>
          <Button onClick={toggleNavBar}>Menu</Button>
        </Col>
      </Row>
      <Row>
        <Container>
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
      </Row>
    </Container>
  );
}
