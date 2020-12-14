import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import { Nav } from 'react-bootstrap';
import routes from '../../constants/routes.json';
import SubMenu from './SubMenu';
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
          <Nav.Link href={routes.HOME}>Accueil</Nav.Link>
          <Nav.Link href={routes.SCRIPT}>Scripts</Nav.Link>
        </Nav.Item>

        <SubMenu
          title="Mes Scripts"
          items={[
            {
              title: 'Consulter',
              link: routes.MY_SCRIPT,
            },
            {
              title: 'Ajouter',
              link: routes.ADD_SCRIPT,
            },
          ]}
        />
      </Nav>
    </div>
  );
};

export default SideBar;
