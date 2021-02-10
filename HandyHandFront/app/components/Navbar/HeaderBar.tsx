import React, { useState } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';
import { Button, Nav } from 'react-bootstrap';
import routes from '../../constants/routes.json';
import styles from './HeaderBar.css';
import { hasToken, removeToken } from '../../features/connection/Connexion';

type Props = {
  toggleSidebar: Function;
  childrenName?: string;
};

const HeaderBar = ({ toggleSidebar, childrenName }: Props) => {
  const [connected, setConnected] = useState(hasToken());

  return (
    <Navbar className={styles.nav}>
      <Navbar.Brand>
        <Button variant="secondary" onClick={() => toggleSidebar()}>
          ☰ HandyHand
          {childrenName == '' || childrenName == null ? '' : `/${childrenName}`}
        </Button>
      </Navbar.Brand>
      <Nav className="mr-auto" />
      <Nav>
        { !connected ? (
          <div>
            <Link to={routes.CONNECTION} className="btn btn-primary">
              Connexion
            </Link>
            <Link to={routes.REGISTER} className="btn btn-secondary">
              Register
            </Link>
          </div>
        ) : (
          <Button
            onClick={() => {
              removeToken();
              setConnected(hasToken());
            }}
            className="btn btn-primary"
          >
            Disconnect
          </Button>
        )}
      </Nav>
    </Navbar>
  );
};

export default HeaderBar;
