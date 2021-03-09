import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import {Link, useHistory} from 'react-router-dom';
import { Button, Nav } from 'react-bootstrap';
import routes from '../../constants/routes.json';
import styles from './HeaderBar.css';
import {getAuthedHeader} from "../../features/connection/Connexion";
import {getAddress} from "../../utils/HandyHandAPI/HandyHandConfig";

type Props = {
  toggleSidebar: Function;
  disconnect: Function;
  childrenName?: string;
  isConnected: boolean;
};

const HeaderBar = (props: Props) => {
  const history = useHistory();

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
        {!props.isConnected ? (
          <div>
            <Link to={routes.CONNECTION} className="btn btn-primary">
              Connexion
            </Link>
            <Link to={routes.REGISTER} className="btn btn-secondary">
              Register
            </Link>
          </div>
        ) : (
          <div>
            <Button
              onClick={() => {
                fetch(`${getAddress()}/env/sync`, {
                  method: 'GET',
                  headers: getAuthedHeader(),
                }).then(() => history.push(routes.MY_SCRIPT));
              }}
              className="btn btn-secondary"
            >
              Sync Env
            </Button>
            <Button
              onClick={() => props.disconnect()}
              className="btn btn-primary"
            >
              Disconnect
            </Button>
          </div>
        )}
      </Nav>
    </Navbar>
  );
};

export default HeaderBar;
