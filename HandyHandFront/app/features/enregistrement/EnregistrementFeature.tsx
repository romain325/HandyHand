import React from 'react';
import { Link } from 'react-router-dom';
import styles from './EnregistrementFeature.css';
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
        <Form>
          <h2>Enregistrement</h2>
          <h6>Crée un nouveau compte</h6>
          <Form.Group controlId="formBasicEmail" >
            <Form.Control type="email" placeholder="Pseudo" />
          </Form.Group>

          <Form.Group controlId="formBasicPassword" >
            <Form.Control type="password" placeholder="Mot de passe" />
          </Form.Group>

          <Form.Group controlId="formBasicPassword" >
            <Form.Control type="password" placeholder="Confirmer mot de passe" />
          </Form.Group>

          <Button type="submit" variant="primary"  block>
            Crée un compte
          </Button>
          <Form.Text className="text-muted">
              Si vous avez déja un compte cliquez-<a>ici</a>
          </Form.Text>
        </Form>
    </div>
  );
}
