import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';
import { Button, Nav } from 'react-bootstrap';
import routes from '../../constants/routes.json';
import styles from './HeaderBar.css';

type Props = {
  toggleSidebar: Function;
  disconnect: Function;
  childrenName?: string;
  isConnected: boolean;
};

const HeaderBar = (props: Props) => {
  return (
    <Navbar className={styles.nav}>
      <Navbar.Brand>
        <Button variant="secondary" onClick={() => props.toggleSidebar()}>
          ☰ HandyHand
          { props.childrenName == '' || props.childrenName == null ? '' : `/${props.childrenName}`}
        </Button>
      </Navbar.Brand>
      <Nav className="mr-auto" />
      <Nav>
        { !props.isConnected ? (
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
            onClick={() => props.disconnect()}
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
