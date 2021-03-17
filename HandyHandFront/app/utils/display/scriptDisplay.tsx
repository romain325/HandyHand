import { Col, Row } from 'react-bootstrap';
import React from 'react';
import { GestureCard, ScriptCard } from '../HandyHandAPI/HandyHandAPIType';
import CardScript from '../../components/CardScript';
import LineScript from '../../components/LineScript';
import CardGesture from '../../components/CardGesture';
import HandyHandAPI from '../HandyHandAPI/HandyHandAPI';

/**
 * Standardize name display
 * @param name
 */
export function propsNameToDisplayName(name: string): string {
  return name.split('/').reverse()[0].split('.')[0];
}

/**
 * Get A list of script as cards
 * @param items scripts
 * @param gestures all gestures
 * @param isOnline is online
 * @param refreshCards exec on refersh
 */
export function allCards(
  items: ScriptCard[],
  gestures: Map<string, string>,
  isOnline: boolean,
  refreshCards: () => void
): JSX.Element {
  const elements: JSX.Element[] = [];

  let i: number = items.length;
  while (i > 0) {
    const subElements: JSX.Element[] = [];
    const iter: number = i < 3 ? i : 3;
    let currentObj: ScriptCard;

    for (let j = 0; j < iter; j++) {
      currentObj = items[(i - items.length) * -1 + j];
      subElements.push(
        <Col>
          <CardScript
            key={currentObj.id}
            title={currentObj.file}
            description={currentObj.description}
            id={currentObj.id}
            gestureId={currentObj.idGesture}
            isActive={currentObj.status == 'true'}
            gestureSet={gestures}
            onGestureSelect={(gestureId, scriptId) => {
              new HandyHandAPI()
                .modifyScript(
                  {
                    oldId: scriptId,
                    idGesture: gestureId,
                  },
                  isOnline
                )
                .then(() => {
                  refreshCards();
                });
            }}
            onDeleteClic={(scriptId) => {
              new HandyHandAPI()
                .removeScript(scriptId, isOnline)
                .then(() => refreshCards());
            }}
            onActiveClic={(scriptId, isActive) =>
              new HandyHandAPI().switchScript(
                scriptId,
                isActive == null ? false : isActive,
                isOnline
              )
            }
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

/**
 * Get a list of script as list
 * @param items scripts
 * @param gestures all gestures
 * @param isOnline is online
 * @param refreshCards exec on refersh
 */
export function allList(
  items: ScriptCard[],
  gestures: Map<string, string>,
  isOnline: boolean,
  refreshCards: () => void
): JSX.Element {
  const elements: JSX.Element[] = [];
  for (let i = 0; i < items.length; i++) {
    elements.push(
      <Row>
        <Col>
          <LineScript
            key={items[i].id}
            title={propsNameToDisplayName(items[i].file)}
            description={items[i].description}
            id={items[i].id}
            gestureId={items[i].idGesture}
            isActive={items[i].status == 'true'}
            gestureSet={gestures}
            onGestureSelect={(gestureId, scriptId) => {
              new HandyHandAPI()
                .modifyScript(
                  {
                    oldId: scriptId,
                    idGesture: gestureId,
                  },
                  isOnline
                )
                .then(() => refreshCards());
            }}
            onDeleteClic={(scriptId) =>
              new HandyHandAPI()
                .removeScript(scriptId, isOnline)
                .then(() => refreshCards())
            }
            onActiveClic={(scriptId, isActive) =>
              new HandyHandAPI().switchScript(
                scriptId,
                isActive == null ? false : isActive,
                isOnline
              )
            }
          />
        </Col>
      </Row>
    );
  }

  return <div>{elements}</div>;
}

/**
 * Get a list of gestures as cards
 * @param items gestures
 * @param isOnline is online
 */
export function allGestureCards(
  items: GestureCard[],
  isOnline: boolean
): JSX.Element {
  const elements: JSX.Element[] = [];

  let i: number = items.length;
  while (i > 0) {
    const subElements: JSX.Element[] = [];
    const iter: number = i < 3 ? i : 3;
    let current: GestureCard;
    for (let j = 0; j < iter; j++) {
      current = items[(i - items.length) * -1 + j];
      subElements.push(
        <Col>
          <CardGesture
            key={current.id}
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
