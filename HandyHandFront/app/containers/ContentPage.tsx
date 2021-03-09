import React, { ReactNode, useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import HeaderBar from '../components/Navbar/HeaderBar';
import SideBar from '../features/sideBar/SideBar';
import {hasToken, removeToken} from '../features/connection/Connexion';

type Props = {
  children: ReactNode;
  childrenName?: string;
};

const ContentPage = (props: Props) => {
  const { children, childrenName } = props;
  const [isOpen, setOpen] = useState<boolean>(false);
  const [isConnected, setConnected] = useState<boolean>(hasToken());

  function disconnect() {
    removeToken();
    setConnected(hasToken());
  }

  function toggleNavBar() {
    setOpen(!isOpen);
  }

  return (
    <div className="fullHeight">
      <HeaderBar toggleSidebar={toggleNavBar} childrenName={childrenName} isConnected={isConnected} disconnect={disconnect} />
      <Row className="fullHeight">
        <Col md={isOpen ? 2 : 0}
          style={{
            display: `${isOpen ? 'inherit' : 'none'}`,
          }}
        >
          <SideBar isOpen={isOpen} toggleBar={toggleNavBar} isConnected={isConnected} />
        </Col>
        <Col md={isOpen ? 10 : 12}>
          {children}
        </Col>
      </Row>
    </div>
  )
}

export default ContentPage;
