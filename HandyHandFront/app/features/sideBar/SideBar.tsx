import React from 'react';
import { Button, Nav, OverlayTrigger, Tooltip } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';
import style from './SideBar.css';

type Props = {
  isOpen: boolean;
  // eslint-disable-next-line @typescript-eslint/ban-types
  toggleBar: Function;
  isConnected: boolean;
};

const SideBar = ({ isOpen, toggleBar, isConnected }: Props) => {
  return (
    <div className={`${style.sidebar} ${isOpen ? style.isopen : ''}`}>
      <div className={style.sidebarheader}>
        <Button variant="link" onClick={() => toggleBar()} className="mt-4">
          LOGO
        </Button>
        <h3>Menu</h3>
      </div>

      <Nav className="flex-column pt-2">
        <Nav.Item className={style.active}>
          <Nav.Link>
            <Link to={routes.HOME}>Home</Link>
          </Nav.Link>
          <Nav.Link>
            {isConnected ? (
              <Link to={routes.SCRIPTS}>Scripts</Link>
            ) : (
              <OverlayTrigger
                placement="right"
                overlay={
                  <Tooltip id="connect_tooltip">
                    You need to be connected
                  </Tooltip>
                }
              >
                <Link to="#">Scripts</Link>
              </OverlayTrigger>
            )}
          </Nav.Link>
          <Nav.Link>
            <Link to={routes.MY_SCRIPT}>My Scripts</Link>
          </Nav.Link>
          <Nav.Link>
            <Link to={routes.SETTINGS}>Settings</Link>
          </Nav.Link>
        </Nav.Item>
      </Nav>
    </div>
  );
};

export default SideBar;
