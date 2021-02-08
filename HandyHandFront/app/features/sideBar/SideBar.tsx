import React from 'react';
import { Button, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';
import style from './SideBar.css';

type Props = {
  isOpen: boolean;
  toggleBar: Function;
};

const SideBar = ({ isOpen, toggleBar }: Props) => {
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
            <Link to={routes.SCRIPTS}>Scripts</Link>
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
