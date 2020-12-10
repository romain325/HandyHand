import React, { ReactNode, useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import HeaderBar from '../components/Navbar/HeaderBar';
import SideBar from '../features/SideBar/SideBar';

type Props = {
  children: ReactNode;
};

const ContentPage = (props: Props) => {
  const { children } = props;
  const [isOpen, setOpen] = useState<boolean>(true);

  function toggleNavBar() {
    setOpen(!isOpen);
  }

  return (
    <div className="mh-100">
      <HeaderBar toggleSidebar={toggleNavBar} />
      <Row className="mh-100">
        <Col md={4}
          style={{
            display:`${isOpen ? 'inherit' : 'none'}`
          }}
        >
          <SideBar isOpen={isOpen} toggleBar={toggleNavBar}/>
        </Col>
        <Col>
          {children}
        </Col>
      </Row>
    </div>
  )
}

export default ContentPage;
