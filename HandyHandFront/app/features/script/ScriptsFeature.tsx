import React, { useState } from 'react';
import { Col, Container, Form } from 'react-bootstrap';
import { Row } from 'react-bootstrap';
import CardScript from '../../components/CardScript';
import ContentPage from '../../containers/ContentPage';
import styles from './ScriptsFeature.css';
import LineScript from '../../components/LineScript';

export default function ScriptsFeatures() {
  const [isGrid, setIsGrid] = useState(true);
  const nbElement : number = 15;

  function allCards() : JSX.Element {

    var elements : JSX.Element[] = [];

    let i : number = nbElement;
    while(i > 0) {

      var subElements : JSX.Element[] = [];
      var iter : number = i < 3 ? i : 3;

      for(let j = 0; j < iter; j++){
        subElements.push(<Col><CardScript/></Col>);
      }
      if(iter == 2){subElements.push(<Col></Col>)}

      elements.push(<Row>{subElements}</Row>);
      i -= 3;
    }

    return (
      <div>
        {elements}
      </div>
    );
  }

  function allList() : JSX.Element {
    var elements : JSX.Element[] = [];
    for(let i = 0; i < nbElement; i++) {
      elements.push(<Row><LineScript/></Row>);
    }

    return (
      <div>
        {elements}
      </div>
    );
  }

  return (
    <ContentPage>
      <Form className={styles.row}>
        <Form.Check
          type="switch"
          id="isgridswitch"
          label="Mode grille"
          checked={isGrid}
          onClick={(e) => {
            console.log(e);
            setIsGrid(!isGrid);
          }}
        />
      </Form>
      <Container style={{
        overflow: 'scroll',
        overflowX: 'hidden',
        height: '70vh'
      }}>
          {
            isGrid ?
            allCards() :
            allList()
          }
      </Container>
    </ContentPage>
  );


}
