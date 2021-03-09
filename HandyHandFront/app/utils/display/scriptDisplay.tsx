import { Col, Row } from 'react-bootstrap';
import React from 'react';
import {GestureCard, ScriptCard} from '../HandyHandAPI/HandyHandAPIType';
import CardScript from '../../components/CardScript';
import LineScript from '../../components/LineScript';
import CardGesture from "../../components/CardGesture";

export function propsNameToDisplayName(name: string): string {
  return name.split('/').reverse()[0].split('.')[0];
}

export function allCards(items: ScriptCard[]): JSX.Element {
  const elements: JSX.Element[] = [];

  let i: number = items.length;
  while (i > 0) {
    const subElements: JSX.Element[] = [];
    const iter: number = i < 3 ? i : 3;

    for (let j = 0; j < iter; j++) {
      subElements.push(
        <Col>
          <CardScript
            title={items[(i - items.length) * -1 + j].file}
            description={items[(i - items.length) * -1 + j].description}
            id={items[(i - items.length) * -1 + j].id}
          />
        </Col>
      );
    }
    if (iter == 2) {
      subElements.push(<Col />);
    }

    elements.push(<Row>{subElements}</Row>);
    i -= 3;
  }

  return <div>{elements}</div>;
}

export function allList(items: ScriptCard[]): JSX.Element {
  const elements: JSX.Element[] = [];
  for (let i = 0; i < items.length; i++) {
    elements.push(
      <Row>
        <Col>
          <LineScript title={propsNameToDisplayName(items[i].file)} description={items[i].description} id={items[i].id} />
        </Col>
      </Row>
    );
  }

  return <div>{elements}</div>;
}

export function allGestureCards(items: GestureCard[], isOnline: boolean): JSX.Element {
  const elements: JSX.Element[] = [];

  let i: number = items.length;
  while (i > 0) {
    const subElements: JSX.Element[] = [];
    const iter: number = i < 3 ? i : 3;
    let current: GestureCard;
    for (let j = 0; j < iter; j++) {
      current = items[(i - items.length) * -1 +j]
      subElements.push(
        <Col>
          <CardGesture
            title={current.name}
            description={current.description}
            id={current.id}
            distanceImportance={current.isDistanceImportant}
            doubleHand={current.isDoubleHand}
            isOnline={isOnline}
          />
        </Col>
      );
    }
    if (iter == 2) {
      subElements.push(<Col />);
    }

    elements.push(<Row>{subElements}</Row>);
    i -= 3;
  }

  return <div>{elements}</div>;
}
