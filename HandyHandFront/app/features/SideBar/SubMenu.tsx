import React, { useState } from 'react';
import { Nav } from 'react-bootstrap';
import classNames from 'classnames';
import { Accordion } from 'react-bootstrap';
import style from './SideBar.css'

type SubItem = {
  link: string,
  title: string
}

type SubMenuElements = {
  title: string,
  items: Array<SubItem>
}



const SubMenu = ({title, items} : SubMenuElements) => {
  const [isCollapsed, setCollapsed] = useState<boolean>(true);

  function toggleNav(){
    setCollapsed(!isCollapsed);
  }

  return (
    <Nav.Item className={`${!isCollapsed ? style.open : ""}`}>
      <Accordion>
        <Accordion.Toggle
          as={Nav.Link}
          variant="link"
          eventKey="0"
          onClick={toggleNav}
        >
          {title}
        </Accordion.Toggle>

        <Accordion.Collapse eventKey="0">
          <nav className="nav flex-column">
            {
              items.map(item => (
                <a className="nav-link nav-item pl-5"
                  href={item.link}
                  key={item.title}>
                    {item.title}
                </a>
              ))
            }
          </nav>
        </Accordion.Collapse>

      </Accordion>
    </Nav.Item>
  )
}

export default SubMenu;
