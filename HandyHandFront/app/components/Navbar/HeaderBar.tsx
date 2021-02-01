import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import routes from '../../constants/routes.json';
import styles from './HeaderBar.css';
import { Button, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

type Props = {
  toggleSidebar: Function;
};

const HeaderBar = ({ toggleSidebar }: Props) => {

  return (
    <Navbar className={styles.nav}>
      <Navbar.Brand>
        <Button variant="secondary" onClick={() => toggleSidebar()}>
          ☰ HandyHand
        </Button>
      </Navbar.Brand>
      <Nav className="mr-auto">
          url
      </Nav>
      <Nav>
        <Link to={routes.CONNECTION}>
          <Button variant="primary">Connexion</Button>
        </Link>

        <Link to={routes.REGISTER}>
          <Button variant="secondary">Enregistrement</Button>
        </Link>
      </Nav>
    </Navbar>
  );
};

export default HeaderBar;
