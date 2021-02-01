import React from 'react';
import { Link } from 'react-router-dom';
import styles from './ConnectionFeature.css';
import routes from '../../constants/routes.json';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import ContentPage from '../../containers/ContentPage';

export default function ConnectionFeature() {
  return (
    <ContentPage>
      <div>
        <div className={styles.form}>
          <Form>
            <h2>Connexion</h2>
            <h6>Connecté vous avec votre compte</h6>
            <Form.Group controlId="formBasicEmail">
              <Form.Control type="email" placeholder="Pseudo" />
              <Form.Text className="text-muted">
                Entrez votre pseudo de connexion
              </Form.Text>
            </Form.Group>
            <Form.Group controlId="formBasicPassword">
              <Form.Control type="password" placeholder="Mot de passe" />
            </Form.Group>
            <Form.Group controlId="formBasicCheckbox" className="checkbox">
              <Form.Check type="checkbox" label="Se souvenir de moi" />
            </Form.Group>
            <Link to={routes.HOME}>
              <Button type="submit" variant="primary" block >
                Connexion
              </Button>
            </Link>
            <Link to={routes.REGISTER}>
              <Button type="submit" variant="outline-primary" block>
                Crée un compte
              </Button>
            </Link>
          </Form>
        </div>
      </div>
    </ContentPage>
  );
}
