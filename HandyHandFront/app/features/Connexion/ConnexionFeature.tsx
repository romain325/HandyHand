import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Counter.css';
import routes from '../../constants/routes.json';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

export default function Counter() {
  return (
    <div>
      <div className={styles.backButton} data-tid="backButton">
        <Link to={routes.HOME}>
          <i className="fa fa-arrow-left fa-3x" />
        </Link>
      </div>
      <div className={styles.form}>
        <Form>
          <h2>Connexion</h2>
          <h6>Connecté vous avec votre compte</h6>
          <Form.Group controlId="formBasicEmail" >
            <Form.Control type="email" placeholder="Pseudo" />
            <Form.Text className="text-muted">
              Entrez votre pseudo de connexion
            </Form.Text>
          </Form.Group>

          <Form.Group controlId="formBasicPassword" >
            <Form.Control type="password" placeholder="Mot de passe" />
          </Form.Group>

          <Form.Group controlId="formBasicCheckbox" className="checkbox">
            <Form.Check type="checkbox" label="Se souvenir de moi" />
          </Form.Group>

          <Button type="submit" variant="primary"  block>
            Connexion
          </Button>

          <Button type="submit" variant="outline-primary"  block>
            Crée un compte
          </Button>
        </Form>
      </div>
    </div>
  );
}
