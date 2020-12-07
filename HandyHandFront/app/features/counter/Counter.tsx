import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Counter.css';
import routes from '../../constants/routes.json';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

export default function Counter() {
  return (
    <body>
      <div className={styles.backButton} data-tid="backButton">
        <Link to={routes.HOME}>
          <i className="fa fa-arrow-left fa-3x" />
        </Link>
      </div>
      <Form className={styles.rect} >
          <h2>Connexion</h2>
          <Form.Label>
            Connecté vous avec à votre compte
          </Form.Label>
          <Form.Group controlId="name">
            <Form.Control type="text" placeholder="Pseudo" />
          </Form.Group>
          <Form.Group controlId="password">
            <Form.Control type="password" placeholder="Mot de passe" />
          </Form.Group>
          <Form.Group controlId="formBasicCheckbox" className="formGroup">
            <Form.Check type="checkbox" label="Enregistrer mes données"/>
          </Form.Group>
          <Button variant="primary" type="submit">
           Connexion
          </Button>
      </Form>
    </body>
  );
}
