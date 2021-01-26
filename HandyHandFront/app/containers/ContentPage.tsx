import React, { ReactNode, useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import HeaderBar from '../components/Navbar/HeaderBar';
import SideBar from '../features/sideBar/SideBar';

type Props = {
  children: ReactNode;
};

const ContentPage = (props: Props) => {
  const { children } = props;
  const [isOpen, setOpen] = useState<boolean>(false);

  function toggleNavBar() {
    setOpen(!isOpen);
  }

  return (
    <div className="fullHeight">
      <HeaderBar toggleSidebar={toggleNavBar} />
      <Row className="fullHeight">
        <Col md={isOpen ? 4 : 0}
          style={{
            display:`${isOpen ? 'inherit' : 'none'}`
          }}
        >
          <SideBar isOpen={isOpen} toggleBar={toggleNavBar} />
        </Col>
        <Col md={isOpen ? 8 : 12}>
          {children}
        </Col>
      </Row>
    </div>
  )
}

export default ContentPage;
