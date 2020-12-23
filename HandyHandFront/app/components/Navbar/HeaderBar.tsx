import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import { Button, Nav } from 'react-bootstrap';

type Props = {
  toggleSidebar: Function;
}

const HeaderBar = ({toggleSidebar} : Props) => {
  return (
      <Navbar bg="dark" variant="dark">
        <Navbar.Brand>
          <Button
            onClick={() => toggleSidebar()}>
            ☰HandyHand
          </Button>
        </Navbar.Brand>
        <Nav className="mr-auto">
          <p>Affiche</p>
        </Nav>
      </Navbar>
  );
}

export default HeaderBar;
