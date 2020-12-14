import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import routes from '../../constants/routes.json';
import styles from './HeaderBar.css';
import { Button, ButtonGroup, Col, Nav } from 'react-bootstrap';

type Props = {
  toggleSidebar: Function;
};

const HeaderBar = ({ toggleSidebar }: Props) => {
  return (
    <Navbar className={styles.nav}>
      <Navbar.Brand>
        <Button variant="secondary" onClick={() => toggleSidebar()}>☰ HandyHand</Button>
      </Navbar.Brand>
      <Nav className="mr-auto">
        <p>Mettre le chemin</p>
      </Nav>
      <Nav>
        <ButtonGroup>
          <Button variant="primary" href={routes.CONNEXION}>Connexion</Button>
          <Button variant="secondary"  href={routes.ENREGISTREMENT}>Enregistrement</Button>
        </ButtonGroup>
      </Nav>
    </Navbar>
  );
};

export default HeaderBar;
