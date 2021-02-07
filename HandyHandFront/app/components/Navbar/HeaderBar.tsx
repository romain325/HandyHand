import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';
import { Button, Nav } from 'react-bootstrap';
import routes from '../../constants/routes.json';
import styles from './HeaderBar.css';

type Props = {
  toggleSidebar: Function;
  childrenName?: string;
};

const HeaderBar = ({ toggleSidebar, childrenName }: Props) => {

  return (
    <Navbar className={styles.nav}>
      <Navbar.Brand>
        <Button variant="secondary" onClick={() => toggleSidebar()}>
          ☰ HandyHand
        </Button>
      </Navbar.Brand>
      <Nav className="mr-auto">
        <p>{ (childrenName == "" || childrenName == null )? "" : `=> ${ childrenName}` } </p>
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
