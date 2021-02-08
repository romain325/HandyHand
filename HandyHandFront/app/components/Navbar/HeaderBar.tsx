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
          {childrenName == '' || childrenName == null
            ? ''
            : `/${childrenName}`}
        </Button>
      </Navbar.Brand>
      <Nav className="mr-auto" />
      <Nav>
        <Link to={routes.CONNECTION} className="btn btn-primary">
          Connexion
        </Link>

        <Link to={routes.REGISTER} className="btn btn-secondary">
          Register
        </Link>
      </Nav>
    </Navbar>
  );
};

export default HeaderBar;
