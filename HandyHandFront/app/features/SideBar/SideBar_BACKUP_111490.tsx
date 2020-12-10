import React, { useState } from 'react';
<<<<<<< HEAD
import { Button } from 'react-bootstrap';
import { Nav } from 'react-bootstrap';
=======
import { Button, Nav } from 'react-bootstrap';

>>>>>>> 1425a9ff7107a720af0970cb7a4f02ad276356ba
import classNames from 'classnames';
import routes from '../../constants/routes.json';
import SubMenu from './SubMenu';
import style from './SideBar.css';

<<<<<<< HEAD
type props = {
=======
type Props = {
>>>>>>> 1425a9ff7107a720af0970cb7a4f02ad276356ba
  isOpen: boolean;
  toggleBar: Function;
};

<<<<<<< HEAD
const SideBar = ({ isOpen, toggleBar }: props) => {
=======
const SideBar = ({ isOpen, toggleBar }: Props) => {
>>>>>>> 1425a9ff7107a720af0970cb7a4f02ad276356ba
  return (
    <div className={`${style.sidebar} ${isOpen ? style.isopen : ''}`}>
      <div className={style.sidebarheader}>
        <Button variant="link" onClick={() => toggleBar()} className="mt-4">
          LOGO
        </Button>
        <h3>Nom APP {String(isOpen)} </h3>
      </div>

      <Nav className="flex-column pt-2">
        <p className="ml-3">Head</p>

        <Nav.Item className={style.active}>
          <Nav.Link href={routes.HOME}>First Item</Nav.Link>
        </Nav.Item>

        <SubMenu
          title="TheTitle"
          items={[
            {
              title: 'SubTitle1',
              link: routes.COUNTER,
            },
            {
              title: 'SubTitle2',
              link: routes.CONNEXION,
            },
            {
              title: 'SubTitle3',
              link: routes.HOME,
            },
          ]}
        />
      </Nav>
    </div>
  );
};

export default SideBar;
