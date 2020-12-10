import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';
import { Nav } from 'react-bootstrap';

export default function Navperso() {
  return (
    <div>
    <Navbar bg="dark" variant="dark">
      <Navbar.Brand href="#home">☰HandyHand</Navbar.Brand>
      <Nav className="mr-auto">
        <p>Affiche</p>
      </Nav>
    </Navbar>
    </div>
  );
}
