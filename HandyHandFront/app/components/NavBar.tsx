import React from 'react';
import { Card, Button } from 'react-bootstrap';
import Dropdown from 'react-bootstrap/Dropdown';
import { Link } from 'react-router-dom';
import routes from '../constants/routes.json';
import Fade from 'react-bootstrap/Fade';
import { useState } from 'react';

export default function NavBar(): JSX.Element {
    const [open, setOpen] = useState(false);
  return (
    <>
    <Button
      onClick={() => setOpen(!open)}
      aria-controls="example-fade-text"
      aria-expanded={open}
    >
      Menu
    </Button>
    <Fade in={open}>
      <div id="example-fade-text">
          <Card border="secondary">
            <Card.Header>Accueil</Card.Header>
            <Link to={routes.CONNEXION}/>
          </Card>
          <Card border="secondary">
            <Card.Header>Scripts</Card.Header>
            <Link to={routes.CONNEXION}/>
          </Card>
          <Card border="secondary">
            <Card.Header>Mes Scripts</Card.Header>
            <Link to={routes.CONNEXION}/>
          </Card>
      </div>
    </Fade>
  </>
  );



    /*
    <Dropdown>
      <Dropdown.Toggle variant="primary" id="dropdown-basic">
        <img src="./Menu.png" />
      </Dropdown.Toggle>

      <Dropdown.Menu>
        <Dropdown.Item href="#/action-1">
          <Card border="secondary">
            <Card.Header>Accueil</Card.Header>
            <Link to={routes.CONNEXION}/>
          </Card>
        </Dropdown.Item>
        <Dropdown.Item href="#/action-2">
          <Card border="secondary">
            <Card.Header>Scripts</Card.Header>
            <Link to={routes.CONNEXION}/>
          </Card>
        </Dropdown.Item>
        <Dropdown.Item href="#/action-3">
          <Card border="secondary">
            <Card.Header>Mes Scripts</Card.Header>
            <Link to={routes.CONNEXION}/>
          </Card>
        </Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
    */
}
