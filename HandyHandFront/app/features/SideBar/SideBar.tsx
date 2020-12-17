import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import { Nav } from 'react-bootstrap';
import routes from '../../constants/routes.json';
import SubMenu from './SubMenu';
import style from './SideBar.css';
import { Link } from 'react-router-dom';

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
          <Nav.Link><Link to={routes.CONNEXION}>Accueil</Link></Nav.Link>
          <Nav.Link><Link to={routes.SCRIPT}>Scripts</Link></Nav.Link>
          <Nav.Link><Link to={routes.COUNTER}>MesScripts</Link></Nav.Link>
        </Nav.Item>
      </Nav>
    </div>
  );
};

export default SideBar;
